package com.todayq.official.service

import com.example.dunzotest.BuildConfig
import com.google.gson.GsonBuilder
import com.todayq.official.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {
    private fun getRetrofit(): Retrofit {

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val httpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)


        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()

    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)


}