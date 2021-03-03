package com.s0l.movies.data.network.api

import com.s0l.movies.data.model.dto.MovieDto
import com.s0l.movies.data.network.response.DiscoverMovieResponse
import com.s0l.movies.data.network.response.GenreMovieResponse
import com.s0l.movies.data.network.response.MovieDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {
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
    suspend fun fetchDiscoverMovie(@Query("page") page: Int): DiscoverMovieResponse
    /**
     * [Movie Keywords](https://developers.themoviedb.org/3/movie/{movie_id}/)
     *
     * Get details about the movie that have been added to a movie.
     *
     * @param [id] Specify the id of movie id.
     *
     * @return [MovieDto] response
     */
    @GET("/3/movie/{movie_id}?append_to_response=videos,credits,reviews")
    suspend fun fetchMovieDetails(@Path("movie_id") id: Int): MovieDetailsResponse
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
    suspend fun fetchGenreMovie(): GenreMovieResponse

}
