package com.s0l.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.adapters.holders.MovieCardViewHolder
import com.s0l.movies.models.MovieData

class MoviesAdapter :
    RecyclerView.Adapter<MovieCardViewHolder>() {

    interface MoviesClick {
        fun onMovieClicked(id: Int)
    }

    var listener: MoviesClick? = null

    private var moviesList = mutableListOf<MovieData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCardViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false),
        listener = this.listener
    )

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        holder.bind(movie = moviesList[position])
    }

    override fun getItemCount() = moviesList.size

    fun setUpMovies(list: List<MovieData>) {
        moviesList = list as MutableList<MovieData>
        notifyDataSetChanged()
    }

    fun addMovie(movie: MovieData) {
        moviesList.add(movie)
        notifyDataSetChanged()
    }

    fun addMovie(movie: MovieData, position: Int) {
        moviesList.add(position, movie)
        notifyItemInserted(position)
    }
}
