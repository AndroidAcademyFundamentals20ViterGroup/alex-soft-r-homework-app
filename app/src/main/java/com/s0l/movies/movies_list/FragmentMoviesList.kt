package com.s0l.movies.movies_list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.s0l.movies.R
import kotlinx.android.synthetic.main.fragment_movies_list.*

class FragmentMoviesList : Fragment() {

    companion object {
        fun newInstance() = FragmentMoviesList()
    }

    private var listener: MoviesClick? = null

    private lateinit var viewModel: FragmentMoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentMoviesListViewModel::class.java)
        materialCardView.setOnClickListener {
            listener?.onMovieClicked(it.id)
        }
        retainInstance = true
    }

    fun setClickListener(l: MoviesClick?) {
        listener = l
    }

}