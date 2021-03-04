package com.s0l.movies.di

import android.content.Context
import com.s0l.movies.service.MovieNotifications
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
//This means that the dependencies provided here will be used across the application
@InstallIn(SingletonComponent::class)
object NotificatorModule {

    @Provides
    @Singleton
    fun provideHttpClient(@ApplicationContext context: Context): MovieNotifications {
        return MovieNotifications(context)
    }

}