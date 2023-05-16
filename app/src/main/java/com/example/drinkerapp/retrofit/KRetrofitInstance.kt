package com.example.drinkerapp.retrofit

import CatAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KRetrofitInstance {
    val apii: CatAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://categoriesapi.w3spaces.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatAPI::class.java)
    }
}

