package com.s0l.movies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.s0l.movies.R
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.ui.adapters.MoviesAdapter
import com.s0l.movies.ui.details.FragmentMoviesDetails
import com.s0l.movies.ui.movies_list.FragmentMoviesList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MoviesAdapter.MoviesClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            // Display the fragment as the main content.
            openMoviesList()
        }
    }

    private fun openNewFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction()
            .apply {
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                replace(R.id.persistent_container, fragment)
                if (addToBackStack) addToBackStack(fragment::class.java.simpleName)
                commit()
            }
    }


    private fun openMoviesList() {
        openNewFragment(fragment = FragmentMoviesList(), addToBackStack = false)
    }

    override fun onMovieClicked(movie: MovieEntity) {
        //Show Movies Details
        openNewFragment(fragment = FragmentMoviesDetails.newInstance(movie.id), addToBackStack = true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}