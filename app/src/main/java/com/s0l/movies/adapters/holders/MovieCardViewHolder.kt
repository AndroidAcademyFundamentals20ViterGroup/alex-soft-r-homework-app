package com.s0l.movies.adapters.holders

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatRatingBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.s0l.movies.R
import com.s0l.movies.adapters.MoviesAdapter
import com.s0l.movies.api.Api
import com.s0l.movies.models.entity.Movie

class MovieCardViewHolder(val itemView: View, val listener: MoviesAdapter.MoviesClick?) :
    RecyclerView.ViewHolder(itemView) {

    private val tvAgeRating = itemView.findViewById<MaterialTextView>(R.id.tvAgeRating)
    private val ivIsLiked = itemView.findViewById<AppCompatImageView>(R.id.ivIsLiked)
    private val ivPoster = itemView.findViewById<AppCompatImageView>(R.id.ivPoster)
    private val tvGenre = itemView.findViewById<MaterialTextView>(R.id.tvGenre)
    private val tvReviews = itemView.findViewById<MaterialTextView>(R.id.tvReviews)
    private val tvTitle = itemView.findViewById<MaterialTextView>(R.id.tvTitle)
    private val tvLength = itemView.findViewById<MaterialTextView>(R.id.tvLength)
    private val ratingBar = itemView.findViewById<AppCompatRatingBar>(R.id.ratingBar)

    @SuppressLint("SetTextI18n")
    fun bind(movie: Movie) {
        tvTitle.text = movie.title
        tvAgeRating.text = if (movie.adult) "16 +" else "13 +"
        tvGenre.text = movie.genres?.joinToString(separator = ", ") { it.name.capitalize() }
        tvReviews.text = "${movie.vote_count} reviews"
        if (movie.runtime != 0)
            tvLength.text = "${movie.runtime} min"
//        else
//            tvLength.visibility = View.GONE

        ratingBar.rating = movie.vote_average / 2

        itemView.setOnClickListener { listener?.onMovieClicked(movie = movie) }

        //setLiked(isLiked = movie.isLiked)
        setPoster(movie = movie)
    }

    private fun setPoster(movie: Movie) {
        movie.poster_path?.let {
            ivPoster.apply {
                clear()
                load(Api.getPosterPath(it)) {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_local_play_24)
                }
            }
        } ?: ivPoster.apply {
            clear()
            load(R.drawable.ic_baseline_local_play_24)
        }
    }

    private fun setLiked(isLiked: Boolean) {
        when {
            isLiked -> ivIsLiked.setImageDrawable(
                ContextCompat.getDrawable(
                    this.itemView.context,
                    R.drawable.ic_baseline_favorite_red_24
                )
            )
            else -> ivIsLiked.setImageDrawable(
                ContextCompat.getDrawable(
                    this.itemView.context,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        }
    }
}
