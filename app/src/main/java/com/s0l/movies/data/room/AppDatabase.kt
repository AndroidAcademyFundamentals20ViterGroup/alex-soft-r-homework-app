package com.s0l.movies.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.s0l.movies.data.model.entity.ActorEntity
import com.s0l.movies.data.model.entity.GenreEntity
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.data.model.entity.MoviesRemoteKeysEntity
import com.s0l.movies.data.room.converters.DataConverter
import com.s0l.movies.data.room.dao.MovieDao
import com.s0l.movies.data.room.dao.MoviesRemoteKeysDao
import java.util.concurrent.Executors

@Database(
    entities = [
        ActorEntity::class,
        GenreEntity::class,
        MovieEntity::class,
        MoviesRemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [DataConverter::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MovieDao
    abstract fun remoteKeysDao(): MoviesRemoteKeysDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "moviesdb")
                .fallbackToDestructiveMigration()
                .setTransactionExecutor(Executors.newSingleThreadExecutor())
                .setQueryExecutor(Executors.newSingleThreadExecutor())
                .allowMainThreadQueries()//for tests only
                .build()
    }
}
