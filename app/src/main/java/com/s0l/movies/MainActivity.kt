package com.s0l.movies

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.s0l.movies.adapters.MoviesAdapter
import com.s0l.movies.details.FragmentMoviesDetails
import com.s0l.movies.movies_list.FragmentMoviesList

class MainActivity : AppCompatActivity(), MoviesAdapter.MoviesClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            // Display the fragment as the main content.
            supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.persistent_container, FragmentMoviesList())
                    commit()
                }
        } else {
            //Restore fragments after configuration changes
            supportFragmentManager.beginTransaction()
                .apply {
                    supportFragmentManager.fragments.map { add(R.id.persistent_container, it) }
                    commit()
                }
        }
    }

    override fun onMovieClicked(id: Int) {
        //Show Movies Details
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.persistent_container, FragmentMoviesDetails.newInstance(id))
                commit()
            }
    }

    override fun onBackPressed() {
        //Handle action of Back navigation
        if (supportFragmentManager.fragments.size > 1) {
            val lastFragment = supportFragmentManager.fragments.last()
            supportFragmentManager.beginTransaction()
                .apply {
                    remove(lastFragment)
                    commit()
                }
        } else {
            super.onBackPressed()
        }
    }


}