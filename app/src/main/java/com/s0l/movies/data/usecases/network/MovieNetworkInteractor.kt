package com.s0l.movies.data.usecases.network

import androidx.paging.*
import androidx.room.withTransaction
import coil.annotation.ExperimentalCoilApi
import com.s0l.movies.data.mappers.MovieEntityMapper
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.data.model.entity.MoviesRemoteKeysEntity
import com.s0l.movies.data.network.api.MoviesService
import com.s0l.movies.data.network.response.MovieDetailsResponse
import com.s0l.movies.data.paging.remotemediator.MoviesRemoteMediator
import com.s0l.movies.data.paging.remotemediator.MoviesRemoteMediator.Companion.STARTING_PAGE_INDEX
import com.s0l.movies.data.room.AppDatabase
import com.s0l.movies.data.usecases.base.BaseUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieNetworkInteractor @Inject constructor(
    private val moviesService: MoviesService,
    private val appDatabase: AppDatabase,
) : BaseUseCases() {

    companion object {
        private const val PAGE_SIZE = 20
    }

    init {
        Timber.d("Injection MovieNetworkRepository")
    }

//    internal var genres: List<GenreEntity> = emptyList()

/*    private suspend fun loadGenres(): List<GenreEntity> {
        val data = moviesService.fetchGenreMovie()
        val genres = MovieEntityMapper.mapListGenresDtoToEntity(data.genreDto)
        appDatabase.moviesDao().insertGenres(genres)
        return genres
    }*/

    @ExperimentalPagingApi
    fun getMoviesFlow(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MoviesRemoteMediator(this, appDatabase),
            pagingSourceFactory = { appDatabase.moviesDao().getPagingSourceMovies() }
        ).flow
    }

    @ExperimentalPagingApi
    suspend fun loadMoviesPage(page: Int, loadType: LoadType): Boolean = withContext(dispatcher) {
/*        if (genres.isEmpty()) {
            genres = loadGenres()
        }*/

        val response = moviesService.fetchDiscoverMovie(page)

        val isEndOfList = response.page == response.total_pages - 1
        if (!isEndOfList) {
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.moviesDao().clearAllMovies()
                    appDatabase.remoteKeysDao().clearRemoteKeys()
                }
            }
            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (isEndOfList) null else page + 1
            val keys = response.results.map {
                MoviesRemoteKeysEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
            }

            val listWithDetails: List<MovieDetailsResponse> = response.results.map {
                moviesService.fetchMovieDetails(it.id)
            }

            val movies = MovieEntityMapper.mapMoviesDtoToEntity(listWithDetails)
            appDatabase.withTransaction {
                appDatabase.remoteKeysDao().insertAll(keys)
                appDatabase.moviesDao().insertMovies(movies = movies)
            }
        }

        return@withContext isEndOfList
    }

    fun getMovieById(movieId: Int): Flow<MovieEntity> = flow {
        withContext(dispatcher) {
            emit(appDatabase.moviesDao().getMovieById(movieId))
        }
    }.flowOn(dispatcher)

    @ExperimentalCoilApi
    suspend fun loadMoviesAndSave() = withContext(dispatcher) {
/*        if (genres.isEmpty()) {
            genres = loadGenres()
        }*/

        val pagesCount = appDatabase.remoteKeysDao().selectAll().size / PAGE_SIZE

        for (page in 1..pagesCount) {
            val response = moviesService.fetchDiscoverMovie(page)

            val listWithDetails: List<MovieDetailsResponse> = response.results.map {
                moviesService.fetchMovieDetails(it.id)
            }

            listWithDetails.forEach { movie ->
                val isUpdated = appDatabase.moviesDao().updateRating(
                    id_ = movie.id,
                    numberOfRatings = movie.vote_count,
                    ratings = movie.popularity
                ) > 0

                if (!isUpdated) {
                    val key = MoviesRemoteKeysEntity(id = movie.id,
                        prevKey = pagesCount,
                        nextKey = pagesCount + 1)
                    appDatabase.remoteKeysDao().insert(key)
                    appDatabase.moviesDao().insertMovies(
                        movies = MovieEntityMapper.mapMoviesDtoToEntity(listWithDetails)
                    )
                }
            }
        }
    }
}
