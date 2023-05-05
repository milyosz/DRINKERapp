package com.example.drinkerapp.retrofit

import com.example.drinkerapp.pojo.DrinkList
import retrofit2.Call
import retrofit2.http.GET

interface DrinkAPI {

    @GET("random.php")
    fun getRandomDrink():Call<DrinkList>
}