package com.s0l.movies.ui.details

import android.R.attr.minHeight
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.s0l.movies.adapters.PersonAdapter
import com.s0l.movies.api.Api
import com.s0l.movies.databinding.FragmentMoviesDetailsBinding
import com.s0l.movies.models.entity.Movie
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentMoviesDetails : Fragment() {

    companion object {
        private const val MOVIE_BUNDLE = "movie_bundle"

        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            fragment.arguments = bundleOf(MOVIE_BUNDLE to movie)
            return fragment
        }
    }

    private var _binding: FragmentMoviesDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val movie: Movie by lazy { arguments?.get(MOVIE_BUNDLE) as Movie }

    var minHeightCast: Int = 0
    var minHeightCrew: Int = 0

    private val itemDecorator by lazy {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.decorator_actors
            )!!
        )
        itemDecorator
    }

    private val recyclerViewCast by lazy {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rvCast)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.addItemDecoration(itemDecorator)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val newHeight = recyclerView.measuredHeight
                if (0 != newHeight && minHeightCast < newHeight) {
                    // keep track the height and prevent recycler view optimizing by resizing
                    minHeightCast = newHeight
                    recyclerView.minimumHeight = newHeight
                }
            }
        })

        recyclerView
    }

    private val recyclerViewCrew by lazy {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rvCrew)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.addItemDecoration(itemDecorator)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val newHeight = recyclerView.measuredHeight
                if (0 != newHeight && minHeightCrew < newHeight) {
                    // keep track the height and prevent recycler view optimizing by resizing
                    minHeightCrew = newHeight
                    recyclerView.minimumHeight = newHeight
                }
            }
        })
        recyclerView
    }

    private val viewModel: FragmentMoviesDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGUI()

/*        viewModel.getLoadingStageLiveData().observe(viewLifecycleOwner, {
            when (it) {
                is MovesIsLoading -> {

                }
                is MovesIsLoaded -> {
                    val adapter = ActorsAdapter()
                    recyclerView.adapter = adapter
                    binding.tvCast.visibility = View.VISIBLE
                    val crew =( (it.data as Movie).credits?.cast!!).sortedBy { actor -> actor.order }
                    adapter.setUpActors(
                        list = crew
                    )
                }
                is MovesLoadingError -> {
                    Toast.makeText(
                        requireContext(),
                        it.exception,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })*/

//        viewModel.loadMovieDetails(movie.id)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //handle screen rotation
        setupGUI()
    }

    @SuppressLint("SetTextI18n")
    private fun setupGUI() {

        recyclerViewCast.removeAllViews()
        recyclerViewCrew.removeAllViews()
        recyclerViewCast.setItemViewCacheSize(-1)
        recyclerViewCrew.setItemViewCacheSize(-1)
//        recyclerViewCast.setHasFixedSize(true)
//        recyclerViewCrew.setHasFixedSize(true)

        val adapterCast = PersonAdapter()
        recyclerViewCast.adapter = adapterCast
        movie.credits?.let {
            binding.tvCast.visibility = View.VISIBLE
            adapterCast.setUpPerson(list = it.cast)
        }


        val adapterCrew = PersonAdapter()
        recyclerViewCrew.adapter = adapterCrew
        movie.credits?.let {
            binding.tvCrew.visibility = View.VISIBLE
            adapterCrew.setUpPerson(list = it.crew)
        }


        movie.backdrop_path?.let {
            binding.ivPosterBig.apply {
                clear()
                load(Api.getBackdropPath(it)) {
                    crossfade(true)
                    placeholder(R.drawable.ic_baseline_local_play_24)
                }
            }
        }

        binding.tvAgeRating.text = if (movie.adult) "16 +" else "13 +"
        binding.tvTitle.text = movie.title
        binding.tvTags.text = movie.genres?.joinToString(separator = ", ") { it.name.capitalize() }
        binding.ratingBar.rating = movie.vote_average / 2
        binding.tvReviews.text = "${movie.vote_count} reviews"
        binding.tvStory.text = movie.overview
    }
}