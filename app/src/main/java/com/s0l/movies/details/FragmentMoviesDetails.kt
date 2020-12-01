package com.s0l.movies.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntegerRes
import com.s0l.movies.R
import com.s0l.movies.movies_list.MoviesClick

class FragmentMoviesDetails : Fragment() {

    companion object {
        fun newInstance() = FragmentMoviesDetails()
    }

    private lateinit var viewModel: FragmentMoviesDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentMoviesDetailsViewModel::class.java)
    }

}