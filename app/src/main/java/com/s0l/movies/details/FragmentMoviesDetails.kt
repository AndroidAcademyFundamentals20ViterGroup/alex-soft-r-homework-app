package com.s0l.movies.details

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.s0l.movies.R
import com.s0l.movies.adapters.ActorsAdapter
import kotlinx.android.synthetic.main.fragment_movies_details.*


class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(id: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            fragment.arguments = bundleOf(MOVIE_ID to id)
            return fragment
        }
    }

    private val itemDecorator by lazy {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.decorator_actors)!!)
        itemDecorator
    }

    private val recyclerView by lazy{
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rvActors)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.addItemDecoration(itemDecorator)

        recyclerView
    }

    private val viewModel: FragmentMoviesDetailsViewModel by viewModels()

    private val movieId by lazy { arguments?.getInt(MOVIE_ID) ?: 0 }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGUI()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //handle screen rotation
        setupGUI()
    }

    @SuppressLint("SetTextI18n")
    private fun setupGUI(){
        val movie = viewModel.getMovies()[movieId]

        recyclerView.removeAllViews()

        recyclerView.setHasFixedSize(true)

        val adapter = ActorsAdapter()
        recyclerView.adapter = adapter

        if(movieId==0)
            adapter.setUpActors(list = movie.actorInfoList!!)

        movie.posterDrawable?.let { poster ->
            ivPosterBig.apply {
                clear()
                load(poster) {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_local_play_24)
                }
            }
        }
        tvTitle.text = movie.title
        tvTags.text = movie.tags.joinToString(separator = " , ")
        ratingBar.rating = movie.rating
        tvReviews.text = "${movie.reviews} reviews"
        tvStory.text = movie.storyLine
    }
}