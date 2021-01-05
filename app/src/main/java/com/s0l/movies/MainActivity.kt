package com.s0l.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.s0l.movies.adapters.MoviesAdapter
import com.s0l.movies.data.Movie
import com.s0l.movies.details.FragmentMoviesDetails
import com.s0l.movies.movies_list.FragmentMoviesList

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

    override fun onMovieClicked(movie: Movie) {
        //Show Movies Details
        openNewFragment(fragment = FragmentMoviesDetails.newInstance(movie), addToBackStack = true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}