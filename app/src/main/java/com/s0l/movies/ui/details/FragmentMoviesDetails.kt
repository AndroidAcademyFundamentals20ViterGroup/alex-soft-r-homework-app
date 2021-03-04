package com.s0l.movies.ui.details

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.clear
import coil.load
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransform.FADE_MODE_THROUGH
import com.s0l.movies.R
import com.s0l.movies.api.Api
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.databinding.FragmentMoviesDetailsBinding
import com.s0l.movies.ui.adapters.ActorAdapter
import com.s0l.movies.ui.adapters.CrewAdapter
import com.s0l.movies.ui.base.BaseFragment
import com.s0l.movies.utils.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class FragmentMoviesDetails : BaseFragment() {

    companion object {
        private const val MOVIE_ID = "movie_id"

        fun newInstance(movie_id: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            fragment.arguments = bundleOf(MOVIE_ID to movie_id)
            return fragment
        }
    }

    private var _binding: FragmentMoviesDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var minHeightCast: Int = 0
    var minHeightCrew: Int = 0

    private var movieDto: MovieEntity? = null

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    private var isRationaleShown = false

    private val itemDecorator by lazy {
        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.decorator_actors)?.let {
            itemDecorator.setDrawable(it)
        }
        itemDecorator
    }

    private val recyclerViewCast by lazy {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rvCast)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.addItemDecoration(itemDecorator)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val newHeight = recyclerView.measuredHeight
                if (0 != newHeight && minHeightCast < newHeight) {
                    // keep track the height and prevent recycler view optimizing by resizing
                    minHeightCast = newHeight
                    recyclerView.minimumHeight = newHeight
                }
            }
        })

        recyclerView
    }

    private val recyclerViewCrew by lazy {
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rvCrew)
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recyclerView.addItemDecoration(itemDecorator)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val newHeight = recyclerView.measuredHeight
                if (0 != newHeight && minHeightCrew < newHeight) {
                    // keep track the height and prevent recycler view optimizing by resizing
                    minHeightCrew = newHeight
                    recyclerView.minimumHeight = newHeight
                }
            }
        })
        recyclerView
    }

    private val viewModel: FragmentMoviesDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            fadeMode = FADE_MODE_THROUGH
            duration = 350
            setAllContainerColors(ContextCompat.getColor(requireContext(), R.color.black_shadow_80))
            scrimColor = ContextCompat.getColor(requireContext(), R.color.black_shadow_80)
            isElevationShadowEnabled = true
            startElevation = 9f
            endElevation = 9f
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        setupGUI()
        arguments?.let {
            view.transitionName = it.getInt(MOVIE_ID).toString()
            viewModel.loadMovieDetails(it.getInt(MOVIE_ID))
        }
        with(viewModel) {
            launchOnLifecycleScope {
                moviesFlow.collectLatest {
                    movieDto = it
                    updateData()
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //handle screen rotation
        setupGUI()
    }

    @SuppressLint("SetTextI18n")
    private fun setupGUI() {

        recyclerViewCast.removeAllViews()
        recyclerViewCrew.removeAllViews()
        recyclerViewCast.setItemViewCacheSize(-1)
        recyclerViewCrew.setItemViewCacheSize(-1)
//        recyclerViewCast.setHasFixedSize(true)
//        recyclerViewCrew.setHasFixedSize(true)

        updateData()
    }

    private var movieTitle = ""

    private fun updateData() {
        movieDto?.let { movie ->

            val actorAdapter = ActorAdapter()
            recyclerViewCast.adapter = actorAdapter
            movie.takeIf { it.actors.isNotEmpty() }?.let {
                binding.tvCast.visibility = View.VISIBLE
                actorAdapter.setUpPerson(list = it.actors)
            }

            val crewAdaper = CrewAdapter()
            recyclerViewCrew.adapter = crewAdaper
            movie.takeIf { it.crew.isNotEmpty() }?.let {
                binding.tvCrew.visibility = View.VISIBLE
                crewAdaper.setUpPerson(list = it.crew)
            }

            movie.backdrop_path?.let {
                binding.ivPosterBig.apply {
                    clear()
                    load(it) {
                        crossfade(true)
                        placeholder(R.drawable.ic_baseline_local_play_24)
                    }
                }
            }

            binding.tvAgeRating.text = if (movie.adult) "16 +" else "13 +"
            binding.tvTitle.text = movie.title
            binding.tvTags.text =
                movie.genres.joinToString(separator = ", ") { it.name.capitalize() }
            binding.ratingBar.rating = movie.vote_average / 2
            binding.tvReviews.text = "${movie.vote_count} reviews"
            binding.tvStory.text = movie.overview

            binding.btnScheduleWatch.setOnClickListener {
                movieTitle = movie.title
                onAddToCalendar()
            }

            binding.btnWatchTrailer.setOnClickListener {
                onWatchTrailer(movie.videos?.let { Api.getYoutubeVideoPath(it.first().key) } ?: "")
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onAttach(context: Context) {
        super.onAttach(context)

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                onCalendarPermissionGrated()
            } else {
                onCalendarPermissionNotGranted()
            }
        }
    }

    override fun onDetach() {
        requestPermissionLauncher.unregister()
        super.onDetach()
    }

    private fun onAddToCalendar() {
        when {
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_CALENDAR)
                    == PackageManager.PERMISSION_GRANTED -> onCalendarPermissionGrated()
            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CALENDAR) ->
                showCalendarPermissionExplanationDialog()
            isRationaleShown -> showCalendarPermissionDeniedDialog()
            else -> requestCalendarPermission()
        }
    }

    private fun requestCalendarPermission() {
        requestPermissionLauncher.launch(Manifest.permission.WRITE_CALENDAR)
    }

    @RequiresPermission(Manifest.permission.WRITE_CALENDAR)
    private fun onCalendarPermissionGrated() {
        val calendarIntent = Intent(Intent.ACTION_INSERT)
        calendarIntent.type = "vnd.android.cursor.item/event"
        calendarIntent.putExtra(CalendarContract.Events.TITLE,
            getString(R.string.calendar_event_title, movieTitle))
        calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION,
            getString(R.string.button_schedule_watch))
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, System.currentTimeMillis())
        startActivity(calendarIntent)
    }

    private fun onCalendarPermissionNotGranted() {
        showMessage(getString(R.string.permission_not_granted_text))
    }

    private fun showCalendarPermissionExplanationDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.permission_dialog_explanation_text)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                isRationaleShown = true
                requestCalendarPermission()
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun showCalendarPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.permission_dialog_denied_text)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + requireContext().packageName)
                    )
                )
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onWatchTrailer(video: String) {
        if (video.isNotEmpty()) {
            Intent(Intent.ACTION_VIEW, Uri.parse(video)).also {
                startActivity(it)
            }
        } else {
            showMessage(getString(R.string.no_video_found_message))
        }
    }
}