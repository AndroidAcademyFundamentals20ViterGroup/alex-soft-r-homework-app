package com.s0l.movies.api

import com.s0l.movies.models.network.DiscoverMovieResponse
import com.s0l.movies.repository.networkresponse.NetworkResponseBase
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverService {
    /**
     * [Movie Discover](https://developers.themoviedb.org/3/discover/movie-discover)
     *
     *  Discover movies by different types of data like average rating, number of votes, genres and certifications.
     *  You can get a valid list of certifications from the  method.
     *
     *  @param [page] Specify the page of results to query.
     *
     *  @return [DiscoverMovieResponse] response
     */
    @GET("/3/discover/movie?sort_by=popularity.desc&include_adult=true")
//    suspend fun fetchDiscoverMovie(@Query("page") page: Int): NetworkResponseBase<DiscoverMovieResponse, Error>
    suspend fun fetchDiscoverMovie(@Query("page") page: Int): ApiResponse<DiscoverMovieResponse>

}
