package com.s0l.movies

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.s0l.movies.adapters.MoviesAdapter
import com.s0l.movies.data.Movie
import com.s0l.movies.details.FragmentMoviesDetails
import com.s0l.movies.movies_list.FragmentMoviesList
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), MoviesAdapter.MoviesClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            // Display the fragment as the main content.
            supportFragmentManager.beginTransaction()
                .apply {
                    setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                    )
                    replace(R.id.persistent_container, FragmentMoviesList())
                    addToBackStack(null)
                    commit()
                }
        }
    }

    override fun onMovieClicked(movie: Movie) {
        //Show Movies Details
        supportFragmentManager.beginTransaction()
            .apply {
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                replace(R.id.persistent_container, FragmentMoviesDetails.newInstance(movie))
                addToBackStack(null)
                commit()
            }
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}