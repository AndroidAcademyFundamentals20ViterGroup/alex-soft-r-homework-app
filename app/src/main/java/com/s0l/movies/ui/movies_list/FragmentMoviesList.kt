package com.s0l.movies.ui.movies_list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.adapters.MoviesAdapter
import com.s0l.movies.databinding.FragmentMoviesListBinding
import com.s0l.movies.models.entity.Movie
import com.s0l.movies.models.network.GenreMovieResponse
import com.s0l.movies.utils.RecyclerViewPaginator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: FragmentMoviesListViewModel by viewModels()

    private val adapter = MoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initial setup
        setupGUI()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.getLoadingStageLiveData().observe(viewLifecycleOwner, {
            when (it) {
                is MovesIsLoading -> {
                    binding.swipeToRefresh.isEnabled = it.showProgress
                    binding.swipeToRefresh.isRefreshing = it.showProgress
                }
                is MovesIsLoaded -> {
                    when (it.data) {
                        is GenreMovieResponse -> {
                            viewModel.loadMovies2(1)
                        }
                        else -> {
                            binding.swipeToRefresh.isEnabled = false
                            binding.swipeToRefresh.isRefreshing = false
                            adapter.setUpMovies(it.data as List<Movie>)
                        }
                    }
                }
                is MovesLoadingError -> {
                    binding.swipeToRefresh.isEnabled = false
                    binding.swipeToRefresh.isRefreshing = false
                    Toast.makeText(
                        requireContext(),
                        it.exception,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
        viewModel.loadGenres()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesAdapter.MoviesClick) {
            adapter.listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        adapter.listener = null
    }

    private fun setupGUI() {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rvMovies)
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(), getMoviesListColumnCount()
        )
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(
            RecyclerViewPaginator(recyclerView = recyclerView,
                isLoading = { viewModel.isLoading() },
                loadMore = { viewModel.loadMovies2(it) },
                onLast = { false }
            ).apply { currentPage = 1 }
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //handle screen rotation
        setupGUI()
    }

    private fun getMoviesListColumnCount(): Int {
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            requireActivity().display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt()
        return if (width / 192 > 2) width / 192 else 2
    }
}