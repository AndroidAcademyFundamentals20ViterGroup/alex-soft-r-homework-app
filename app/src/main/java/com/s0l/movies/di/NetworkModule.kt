package com.s0l.movies.di

import androidx.annotation.NonNull
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.s0l.movies.BuildConfig
import com.s0l.movies.Constants
import com.s0l.movies.api.DiscoverService
import com.s0l.movies.api.GenreService
import com.s0l.movies.api.MovieDetailService
import com.s0l.movies.repository.networkresponse.NetworkResponseAdapterFactory
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
//This means that the dependencies provided here will be used across the application
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(Constants.Delay_10, TimeUnit.MILLISECONDS)
            .readTimeout(Constants.Delay_10, TimeUnit.MILLISECONDS)
            .writeTimeout(Constants.Delay_10, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @NonNull okHttpClient: OkHttpClient,
//        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(NetworkResponseAdapterFactory())
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
    }

/*    @Provides
    @Singleton
    fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()*/

/*
    @Provides
    @Singleton
    fun provideGson():
            Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson):
            GsonConverterFactory = GsonConverterFactory.create(gson)
*/

/*    @Provides
    @Singleton
    fun provideNullOrEmptyConverterFactory(): Converter.Factory =
        object : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit,
            ): Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(
                    this,
                    type,
                    annotations
                )

                return Converter { body: ResponseBody ->
                    if (body.contentLength() == 0L) null
                    else nextResponseBodyConverter.convert(body)
                }
            }
        }*/

    @Provides
    @Singleton
    fun provideDiscoverService(@NonNull retrofit: Retrofit): DiscoverService {
        return retrofit.create(DiscoverService::class.java)
    }

    @Provides
    @Singleton
    fun provideGenreService(@NonNull retrofit: Retrofit): GenreService {
        return retrofit.create(GenreService::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDetailService(@NonNull retrofit: Retrofit): MovieDetailService {
        return retrofit.create(MovieDetailService::class.java)
    }

}