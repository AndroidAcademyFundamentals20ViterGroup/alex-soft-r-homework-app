package com.s0l.movies.di

import android.content.Context
import androidx.annotation.NonNull
import com.s0l.movies.data.room.AppDatabase
import com.s0l.movies.data.room.dao.MovieDao
import com.s0l.movies.data.room.dao.MoviesRemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
//This means that the dependencies provided here will be used across the application
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getDatabase(appContext)
    }

    @Provides
    @Singleton
    fun provideMovieDao(@NonNull database: AppDatabase): MovieDao {
        return database.moviesDao()
    }

    @Provides
    @Singleton
    fun provideMoviesRemoteKeysDao(@NonNull database: AppDatabase): MoviesRemoteKeysDao {
        return database.remoteKeysDao()
    }

}
