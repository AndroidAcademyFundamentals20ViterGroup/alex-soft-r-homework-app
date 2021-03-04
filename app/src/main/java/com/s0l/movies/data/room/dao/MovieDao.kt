package com.s0l.movies.data.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.s0l.movies.data.model.entity.ActorEntity
import com.s0l.movies.data.model.entity.GenreEntity
import com.s0l.movies.data.model.entity.MovieEntity


@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getPagingSourceMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM movies")
    fun getAllMovies() : List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAllMovies(): Int

    @Query("UPDATE movies SET actors = :actors WHERE id = :id_")
    suspend fun updateMovieWithActors(id_: Int, actors: List<ActorEntity>): Int

    @Query("UPDATE movies SET video = :video WHERE id = :id_")
    suspend fun updateMovieWithVideo(id_: Int, video: String)

    @Query("SELECT * FROM movies WHERE id = :id_")
    suspend fun getMovieById(id_: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActors(actors: List<ActorEntity>)

    @Query("UPDATE movies SET vote_count = :numberOfRatings, vote_average = :ratings WHERE id = :id_")
    suspend fun updateRating(id_: Int, numberOfRatings: Int, ratings: Float): Int

}
