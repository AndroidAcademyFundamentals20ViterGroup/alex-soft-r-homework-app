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
import com.s0l.movies.models.MovieData

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
    fun bind(movie: MovieData) {
        tvTitle.text = movie.title
        tvAgeRating.text = "${movie.ageLimit} +"
        tvGenre.text = movie.tags.joinToString(separator = ",")
        tvReviews.text = "${movie.reviews} reviews"
        tvLength.text = "${movie.length} min"

        ratingBar.rating = movie.rating

        itemView.setOnClickListener { listener?.onMovieClicked(id = adapterPosition) }

        setLiked(isLiked = movie.isLiked)
        setPoster(movie = movie)
    }

    private fun setPoster(movie: MovieData) {
        ivPoster.apply {
            clear()
            load(movie.coverDrawable){
                crossfade(true)
                placeholder(R.drawable.ic_baseline_local_play_24)
            }
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
