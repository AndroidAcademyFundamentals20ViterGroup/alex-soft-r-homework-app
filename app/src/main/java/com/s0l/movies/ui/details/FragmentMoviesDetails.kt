package com.s0l.movies.ui.details

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.s0l.movies.R
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.databinding.FragmentMoviesDetailsBinding
import com.s0l.movies.ui.adapters.ActorAdapter
import com.s0l.movies.ui.adapters.CrewAdapter
import com.s0l.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class FragmentMoviesDetails : BaseFragment() {

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movie_id: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            fragment.arguments = bundleOf(MOVIE_ID to movie_id)
            return fragment
        }
    }

    private var _binding: FragmentMoviesDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var minHeightCast: Int = 0
    var minHeightCrew: Int = 0

    private var movieDto: MovieEntity? = null

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

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGUI()
        arguments?.let {
            viewModel.loadMovieDetails(it.getInt(MOVIE_ID))
        }
        with(viewModel) {
            launchOnLifecycleScope {
                moviesFlow.collectLatest {
                    movieDto = it
                    updateData()
                }
            }
        }
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

        updateData()
    }

    private fun updateData() {
        movieDto?.let {

            val actorAdapter = ActorAdapter()
            recyclerViewCast.adapter = actorAdapter
            it.takeIf { it.actors.isNotEmpty()}?.let {
                binding.tvCast.visibility = View.VISIBLE
                actorAdapter.setUpPerson(list = it.actors)
            }

            val crewAdaper = CrewAdapter()
            recyclerViewCrew.adapter = crewAdaper
            it.takeIf { it.crew.isNotEmpty()}?.let {
                binding.tvCrew.visibility = View.VISIBLE
                crewAdaper.setUpPerson(list = it.crew)
            }

            it.backdrop_path?.let {
                binding.ivPosterBig.apply {
                    clear()
                    load(it) {
                        crossfade(true)
                        placeholder(R.drawable.ic_baseline_local_play_24)
                    }
                }
            }

            binding.tvAgeRating.text = if (it.adult) "16 +" else "13 +"
            binding.tvTitle.text = it.title
            binding.tvTags.text =
                it.genres.joinToString(separator = ", ") { it.name.capitalize() }
            binding.ratingBar.rating = it.vote_average / 2
            binding.tvReviews.text = "${it.vote_count} reviews"
            binding.tvStory.text = it.overview
        }
    }
}