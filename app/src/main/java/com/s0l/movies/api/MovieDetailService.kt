package com.s0l.movies.api

import com.s0l.movies.models.entity.Movie
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailService {
    /**
     * [Movie Keywords](https://developers.themoviedb.org/3/movie/{movie_id}/)
     *
     * Get details about the movie that have been added to a movie.
     *
     * @param [id] Specify the id of movie id.
     *
     * @return [Movie] response
     */
    @GET("/3/movie/{movie_id}?append_to_response=videos,credits,reviews")
    suspend fun fetchMovieDetails(@Path("movie_id") id: Int): ApiResponse<Movie>

}
