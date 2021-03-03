package com.s0l.movies.data.paging.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.data.model.entity.MoviesRemoteKeysEntity
import com.s0l.movies.data.room.AppDatabase
import com.s0l.movies.data.room.dao.MoviesRemoteKeysDao
import com.s0l.movies.data.usecases.network.MovieNetworkInteractor
import java.io.InvalidObjectException

@ExperimentalPagingApi
class MoviesRemoteMediator(
    private val movieInteractor: MovieNetworkInteractor,
    appDatabase: AppDatabase,
) : RemoteMediator<Int, MovieEntity>() {

    private val moviesRemoteKeysDao: MoviesRemoteKeysDao = appDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                //PagingData content being refreshed, which can be a result of PagingSource invalidation,
                // refresh that may contain content updates, or the initial load.
                LoadType.REFRESH -> {
//                    1
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }
                //Load at the start of a PagingData.
                LoadType.PREPEND -> {
//                    return MediatorResult.Success(endOfPaginationReached = true)
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                        ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                    val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )

                    prevKey
                }
                //Load at the end of a PagingData.
                //If the loadType is APPEND, then we are gonna look for the last item in the list
                LoadType.APPEND -> {
//                    val lastItem = state.lastItemOrNull()
//                    val remoteKey: MoviesRemoteKeysEntity? =
//                        if (lastItem?.id != null) {
//                            moviesRemoteKeysDao.getKeysByMovieId(lastItem.id)
//                        } else null
//
//                    if (remoteKey?.nextKey == null) {
//                        return MediatorResult.Success(
//                            endOfPaginationReached = true
//                        )
//                    }
//                    remoteKey.nextKey
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                    val nextKey = remoteKeys.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )

                    nextKey
                }
            }

            MediatorResult.Success(endOfPaginationReached = movieInteractor.loadMoviesPage(page, loadType))

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }

    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieEntity>): MoviesRemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                moviesRemoteKeysDao.getKeysByMovieId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): MoviesRemoteKeysEntity? {
        return state.firstItemOrNull()?.let { moviesRemoteKeysDao.getKeysByMovieId(it.id) }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): MoviesRemoteKeysEntity? {
        return state.lastItemOrNull()?.let { moviesRemoteKeysDao.getKeysByMovieId(it.id) }
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

}