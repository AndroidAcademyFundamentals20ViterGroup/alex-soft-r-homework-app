package com.s0l.movies.ui.movies_list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.s0l.movies.R
import com.s0l.movies.databinding.FragmentMoviesListBinding
import com.s0l.movies.ui.adapters.MoviesAdapter
import com.s0l.movies.ui.adapters.PagingLoadStateAdapter
import com.s0l.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

@AndroidEntryPoint
open class FragmentMoviesList : BaseFragment() {

    companion object {
        const val VERTICAL_SPAN_COUNT = 2
        const val HORIZONTAL_SPAN_COUNT = 4
    }

    private var _binding: FragmentMoviesListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding

    @ExperimentalPagingApi
    private val viewModel: FragmentMoviesListViewModel by viewModels()

    private val adapter = MoviesAdapter()
    private var spanCount = VERTICAL_SPAN_COUNT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enterTransition = MaterialContainerTransform()//MaterialSharedAxis(X, false)
//        exitTransition = MaterialContainerTransform()//MaterialSharedAxis(X, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding?.root!!
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @InternalCoroutinesApi
    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initial setup
        setupGUI()
    }

    @ExperimentalPagingApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        retainInstance = true
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

    @InternalCoroutinesApi
    @ExperimentalPagingApi
    private fun setupGUI() {
        spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            VERTICAL_SPAN_COUNT else HORIZONTAL_SPAN_COUNT

        binding?.swipeToRefresh!!.setOnRefreshListener { adapter.refresh() }

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rvMovies)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter(adapter),
            footer = PagingLoadStateAdapter(adapter)
        )
        with(viewModel) {
            launchOnLifecycleScope {
                moviesFlow.collectLatest { adapter.submitData(it) }
            }
            launchOnLifecycleScope {
                adapter.loadStateFlow.collectLatest {
                    binding?.swipeToRefresh!!.isRefreshing = it.refresh is LoadState.Loading
                }
            }
            launchOnLifecycleScope {
                adapter.loadStateFlow.distinctUntilChangedBy { it.refresh }
                    .filter { it.refresh is LoadState.NotLoading }
                    .collect { recyclerView.scrollToPosition(0) }
            }
        }
    }


    @InternalCoroutinesApi
    @ExperimentalPagingApi
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //handle screen rotation
        setupGUI()
    }
}