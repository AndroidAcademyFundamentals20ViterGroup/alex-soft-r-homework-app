package com.s0l.movies.movies_list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.adapters.MoviesAdapter
import com.s0l.movies.base.ViewModelsFactory
import kotlinx.android.synthetic.main.fragment_movies_list.*
import kotlinx.coroutines.Dispatchers.IO

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: FragmentMoviesListViewModel by viewModels { ViewModelsFactory(MovieInteractor(requireContext(), IO)) }

    private val adapter = MoviesAdapter()

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
                    swipeToRefresh.isEnabled = it.showProgress
                    swipeToRefresh.isRefreshing = it.showProgress
                }
                is MovesIsLoaded -> {
                    swipeToRefresh.isEnabled = false
                    swipeToRefresh.isRefreshing = false
                    adapter.setUpMovies(it.list)
                }
                is MovesLoadingError -> {
                    swipeToRefresh.isEnabled = false
                    swipeToRefresh.isRefreshing = false
                    Toast.makeText(
                        requireContext(),
                        it.exception.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesAdapter.MoviesClick) {
            adapter.listener = context
            viewModel.loadMovies()
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
        //adapter.setUpMovies(list = viewModel.getMovies())
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