package com.s0l.movies.data.mappers

import com.s0l.movies.api.Api
import com.s0l.movies.data.model.dto.CreditsDto
import com.s0l.movies.data.model.dto.GenreDto
import com.s0l.movies.data.model.entity.*
import com.s0l.movies.data.network.response.MovieDetailsResponse

object MovieEntityMapper {

    fun mapListGenresDtoToEntity(genreDto: List<GenreDto>): List<GenreEntity> {
        return genreDto.map { genre ->
            GenreEntity(id = genre.id, name = genre.name)
        }
    }

    private fun mapMovieDtoToEntity(
        movieDto: MovieDetailsResponse
    ): MovieEntity {
        return MovieEntity(
            id = movieDto.id,
            adult = movieDto.adult,
            overview = movieDto.overview,
            release_date = movieDto.release_date,
            original_title = movieDto.release_date,
            original_language = movieDto.original_language,
            title = movieDto.title,
            backdrop_path = movieDto.backdrop_path?.let { Api.getBackdropPath(movieDto.backdrop_path) } ?: "",
            popularity = movieDto.popularity,
            vote_average = movieDto.vote_average,
            video = movieDto.video,
            vote_count = movieDto.vote_count,
            poster_path = movieDto.poster_path?.let { Api.getPosterPath(movieDto.poster_path) } ?: "",
            runtime = movieDto.runtime,
            genres = movieDto.genres.map { GenreEntity(id = it.id, name = it.name) },
            videos = movieDto.videos?.results?.map {  VideoEntity(
                id = it.id,
                name = it.name,
                iso_639_1 = it.iso_639_1,
                iso_3166_1 = it.iso_3166_1,
                site = it.site,
                key = it.key,
                size = it.size,
                type = it.type
            )  }?: listOf(),
            reviews = movieDto.reviews?.results?.map { ReviewEntity(
                id = it.id,
                author = it.author,
                content = it.content,
                url = it.url
            ) }?: listOf(),
            actors = movieDto.credits?.cast?.map {
                ActorEntity(
                    id = it.id,
                    adult = it.adult,
                    gender = it.gender,
                    known_for_department = it.known_for_department,
                    name = it.name,
                    original_name = it.original_name,
                    popularity = it.popularity,
                    profile_path = it.profile_path,
                    cast_id = it.cast_id,
                    character = it.character,
                    credit_id = it.credit_id,
                    order = it.order
                )
            }?: listOf(),
            crew = movieDto.credits?.crew?.map {
                CrewEntity(
                    id = it.id,
                    adult = it.adult,
                    gender = it.gender,
                    known_for_department = it.known_for_department,
                    name = it.name,
                    original_name = it.original_name,
                    popularity = it.popularity,
                    profile_path = it.profile_path,
                    credit_id = it.credit_id,
                    department = it.department,
                    job = it.job
                )
            }?: listOf(),
            homepage = movieDto.homepage,
            imdb_id = movieDto.imdb_id,
            status = movieDto.status,
            tagline = movieDto.tagline,
            spoken_languages = movieDto.spoken_languages.map {
                SpokenLanguagesEntity(
                    english_name = it.english_name,
                    iso_639_1 = it.iso_639_1,
                    name = it.name
                )
            }?: listOf(),
//            author_details = movieDto.author_details?.let {
//                AuthorDetailsEntity(
//                    name = it.name,
//                    username = it.username,
//                    avatar_path = it.avatar_path,
//                    rating = it.rating
//                )
//            }
        )
    }

    fun mapMoviesDtoToEntity(
        movieDtos: List<MovieDetailsResponse>
    ): List<MovieEntity> {
        return movieDtos.map { mapMovieDtoToEntity(it) }
    }

    fun mapActorsDtoToEntity(
        actors: List<CreditsDto.PersonCast>,
    ): List<ActorEntity> {
        return actors.map { actor ->
            ActorEntity(
                id = actor.id,
                name = actor.name,
                profile_path = actor.profile_path?.let { Api.getProfilePath(actor.profile_path) }
                    ?: "",
                adult = false,
                gender = 0,
                known_for_department = "",
                original_name = "",
                popularity = 0f,
                credit_id = "",
            )
        }
    }
}