package com.s0l.movies.di.interseptors

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import com.s0l.movies.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    val originalUrl = originalRequest.url
    val url = originalUrl.newBuilder()
      .addQueryParameter("api_key", BuildConfig.API_KEY)
      .addQueryParameter("language",
        ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0].toString()
          .replace("_","-"))
      .build()

    val requestBuilder = originalRequest.newBuilder().url(url)
    val request = requestBuilder.build()
    return chain.proceed(request)
  }
}
