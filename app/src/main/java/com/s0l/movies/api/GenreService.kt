package com.s0l.movies.api

import com.s0l.movies.models.network.DiscoverMovieResponse
import com.s0l.movies.models.network.GenreMovieResponse
import com.s0l.movies.repository.networkresponse.NetworkResponseBase
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GenreService {
    /**
     * [Movie Discover](https://developers.themoviedb.org/3/genre/movie/list)
     *
     * Get the genre keywords that have been added to a movie.
     *
     *  @param [page] Specify the page of results to query.
     *
     *  @return [DiscoverMovieResponse] response
     */
    @GET("/3/genre/movie/list")
    suspend fun fetchGenreMovie(): ApiResponse<GenreMovieResponse>

}
