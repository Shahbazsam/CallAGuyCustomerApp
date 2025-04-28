package com.example.callaguy.core.constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient.Builder

object Constants {
    const val BASE_URL = "https://example.com"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client : OkHttpClient =OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
}