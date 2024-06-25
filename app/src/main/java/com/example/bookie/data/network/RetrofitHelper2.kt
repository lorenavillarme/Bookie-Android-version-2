package com.example.bookie.data.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitHelper2 {

    private var retrofit: Retrofit? = null
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}
