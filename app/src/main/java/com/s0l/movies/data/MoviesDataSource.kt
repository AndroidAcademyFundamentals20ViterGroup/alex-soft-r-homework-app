package com.s0l.movies.data

import com.s0l.movies.R
import com.s0l.movies.models.ActorInfo
import com.s0l.movies.models.MovieData

class MoviesDataSource {
    fun getMovies(): List<MovieData> = listOf(
        MovieData(
            id = 1,
            ageLimit = 13,
            isLiked = false,
            coverDrawable = R.drawable.poster_avengers,
            posterDrawable = R.drawable.avengers_orig,
            rating = 4f,
            reviews = 125,
            title = "Avengers: End Game",
            length = 137,
            tags = listOf("Action", "Adventure", "Drama") ,
            actorInfoList = ActorsDataSource().getActors(),
            storyLine = "After the devastating events of Avengers: Infinity War, the universe is "
                    + "in ruins. With the help of remaining allies, the Avengers assemble once "
                    + "more in order to reverse Thanos\\' actions and restore balance to the universe."
        ),
        MovieData(
            id = 2,
            ageLimit = 16,
            isLiked = true,
            coverDrawable = R.drawable.poster_tenet,
            rating = 5f,
            reviews = 98,
            title = "Tenet",
            length = 97,
            tags = listOf("Action", "Sci-Fi", "Thriller")
        ),
        MovieData(
            id = 3,
            ageLimit = 13,
            isLiked = false,
            coverDrawable = R.drawable.poster_black_widow,
            rating = 4f,
            reviews = 38,
            title = "Black Widow",
            length = 102,
            tags = listOf("Action", "Adventure", "Sci-Fi")
        ),
        MovieData(
            id = 4,
            ageLimit = 13,
            isLiked = false,
            coverDrawable = R.drawable.poster_wonder_woman,
            rating = 5f,
            reviews = 74,
            title = "Wonder Woman 1984",
            length = 120,
            tags = listOf("Action", "Adventure", "Fantasy")
        )
    )
}
