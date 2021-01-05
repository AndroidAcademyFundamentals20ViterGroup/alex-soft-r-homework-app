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
import com.s0l.movies.data.Movie
import kotlinx.android.synthetic.main.fragment_movies_details.*


class FragmentMoviesDetails : Fragment(R.layout.fragment_movies_details) {

    companion object {
        private const val MOVIE_BUNDLE = "movie_bundle"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            fragment.arguments = bundleOf(MOVIE_BUNDLE to movie)
//            val bundle = Bundle()
//            bundle.putParcelable(MOVIE_BUNDLE, movie)
//            fragment.arguments = bundle
            return fragment
        }
    }

    private val movie: Movie by lazy { arguments?.get(MOVIE_BUNDLE) as Movie }

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

        recyclerView.removeAllViews()

        recyclerView.setHasFixedSize(true)

        val adapter = ActorsAdapter()
        recyclerView.adapter = adapter

        if(movie.actors.isNotEmpty()) {
            tvCast.visibility = View.VISIBLE
            adapter.setUpActors(list = movie.actors)
        } else {
            tvCast.visibility = View.GONE
        }

        ivPosterBig.apply {
            clear()
            load(movie.backdrop) {
                crossfade(true)
                placeholder(R.drawable.ic_baseline_local_play_24)
            }
        }

        tvAgeRating.text = "${movie.minimumAge} +"
        tvTitle.text = movie.title
        tvTags.text = movie.genres.joinToString(separator = ", "){it.name}
        ratingBar.rating = movie.ratings /2
        tvReviews.text = "${movie.numberOfRatings} reviews"
        tvStory.text = movie.overview
    }
}