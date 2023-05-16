package com.example.drinkerapp.retrofit

import com.example.drinkerapp.pojo.DrinkList
import com.example.drinkerapp.pojo.DrinksByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkAPI {

    @GET("random.php")
    fun getRandomDrink():Call<DrinkList>

    @GET("lookup.php")
    fun getDrinkDetails(@Query("i") id:String) : Call<DrinkList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName:String):Call<DrinksByCategoryList>



    @GET("filter.php")
    fun getDrinksByCategory(@Query("i") categoryName: String) : Call<DrinksByCategoryList>

    @GET("search.php")
    fun searchDrinks(@Query("s") searchQuery: String):Call<DrinkList>
}
