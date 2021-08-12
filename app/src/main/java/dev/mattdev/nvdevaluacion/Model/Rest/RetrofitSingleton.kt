package dev.mattdev.nvdevaluacion.Model.Rest

import dev.mattdev.nvdevaluacion.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitSingleton {
    val request: RetrofitRequest = Retrofit.Builder()
        .baseUrl(BuildConfig.NASA_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)
            .build())
        .build()
        .create(RetrofitRequest::class.java)
}