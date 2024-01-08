package com.example.wz_tracker.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MwApiService {

    private const val CONNECT_TIMEOUT = 10L
    private const val READ_TIMEOUT = 10L

    private const val BASE_URL = "https://call-of-duty-modern-warfare.p.rapidapi.com/"

    private val okHttpClient = buildOkHttpClient()
    private val retrofit = buildRetrofit()

    val retrofitService: MwApi by lazy {
        retrofit.create(MwApi::class.java)
    }

    private fun buildOkHttpClient() =
        OkHttpClient
            .Builder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()

    private fun buildRetrofit() =
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

}
