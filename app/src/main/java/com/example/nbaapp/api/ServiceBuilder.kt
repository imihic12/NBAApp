package com.example.nbaapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val loggingInterceptor = HttpLoggingInterceptor().also {
        it.setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    private val retrofit = Retrofit
        .Builder()
        .baseUrl("https://www.balldontlie.io/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
