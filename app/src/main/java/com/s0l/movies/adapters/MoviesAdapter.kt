package com.s0l.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.adapters.holders.MovieCardViewHolder
import com.s0l.movies.data.Movie

class MoviesAdapter :
    RecyclerView.Adapter<MovieCardViewHolder>() {

    interface MoviesClick {
        fun onMovieClicked(movie: Movie)
    }

    var listener: MoviesClick? = null

    private var moviesList = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MovieCardViewHolder(
        itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_holder_movie, parent, false),
        listener = this.listener
    )

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        holder.bind(movie = moviesList[position])
    }

    override fun getItemCount() = moviesList.size

    fun setUpMovies(list: List<Movie>) {
        moviesList = list as MutableList<Movie>
        notifyDataSetChanged()
    }

    fun addMovie(movie: Movie) {
        moviesList.add(movie)
        notifyDataSetChanged()
    }

    fun addMovie(movie: Movie, position: Int) {
        moviesList.add(position, movie)
        notifyItemInserted(position)
    }
}
