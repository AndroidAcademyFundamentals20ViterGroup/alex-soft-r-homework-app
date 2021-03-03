package com.s0l.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.s0l.movies.R
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.ui.adapters.holders.MovieCardViewHolder

object MovieComparator : DiffUtil.ItemCallback<MovieEntity>() {
    override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
        return oldItem == newItem
    }
}

class MoviesAdapter : PagingDataAdapter<MovieEntity, MovieCardViewHolder>(MovieComparator) {

    interface MoviesClick {
        fun onMovieClicked(movie: MovieEntity)
    }

    var listener: MoviesClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieCardViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_movie, parent, false),
            listener = this.listener
        )

    override fun onBindViewHolder(holder: MovieCardViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}


