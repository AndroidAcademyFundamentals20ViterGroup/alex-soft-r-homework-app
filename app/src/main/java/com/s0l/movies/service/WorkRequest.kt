package com.s0l.movies.service

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkRequest(private val appContext: Context) {

    private val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresCharging(false)
        .setRequiresBatteryNotLow(false)
        .setRequiresDeviceIdle(false)
        .build()

    private val periodicRequest = PeriodicWorkRequestBuilder<MoviesWorker>(
        REPEAT_INTERVAL, TimeUnit.MINUTES, FLEX_INTERVAL, TimeUnit.MINUTES
    ).setConstraints(constraints).build()

    fun start() {
        WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
                MOVIES_PERIODIC_WORK, ExistingPeriodicWorkPolicy.REPLACE, periodicRequest
        )
    }

    companion object {
        private const val MOVIES_PERIODIC_WORK = "MOVIES_PERIODIC_WORK"
        private const val REPEAT_INTERVAL = 15L
        private const val FLEX_INTERVAL = 5L

        fun getInstance(appContext: Context) = WorkRequest(appContext)
    }

}