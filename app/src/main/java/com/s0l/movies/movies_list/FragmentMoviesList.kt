package com.s0l.movies.movies_list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.adapters.MoviesAdapter

class FragmentMoviesList : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: FragmentMoviesListViewModel by viewModels()

    private val adapter = MoviesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initial setup
        setupGUI()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MoviesAdapter.MoviesClick)
            adapter.listener = context
    }

    override fun onDetach() {
        super.onDetach()
        adapter.listener = null
    }

    private fun setupGUI(){
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rvMovies)
        recyclerView.layoutManager = GridLayoutManager(
            requireContext(), getMoviesListColumnCount()
        )
        recyclerView.adapter = adapter
        adapter.setUpMovies(list = viewModel.getMovies())
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