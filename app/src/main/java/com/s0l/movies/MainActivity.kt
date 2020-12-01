package com.s0l.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.s0l.movies.details.FragmentMoviesDetails
import com.s0l.movies.movies_list.FragmentMoviesList
import com.s0l.movies.movies_list.MoviesClick

class MainActivity : AppCompatActivity(), MoviesClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            // Display the fragment as the main content.
            val rootFragment =
                FragmentMoviesList.newInstance().apply { setClickListener(this@MainActivity) }
            supportFragmentManager.beginTransaction()
                .apply {
                    add(R.id.persistent_container, rootFragment)
                    commit()
                }
        } else {
            //Restore fragments after configuration changes
            supportFragmentManager.beginTransaction()
                .apply {
                    supportFragmentManager.fragments.map { add(R.id.persistent_container, it) }
                    commit()
                }
            restoreClickListener()
        }
    }

    override fun onMovieClicked(id: Int) {
        //Show Movies Details
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.persistent_container, FragmentMoviesDetails.newInstance())
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
            restoreClickListener()
        } else {
            super.onBackPressed()
        }
    }

    private fun restoreClickListener() {
        val rootFragment = supportFragmentManager.findFragmentById(R.id.persistent_container)
        if (rootFragment is FragmentMoviesList && rootFragment.isAdded)
            rootFragment.setClickListener(this@MainActivity)
    }

}