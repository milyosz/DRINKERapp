package com.example.drinkerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drinkerapp.pojo.DrinksByCategory
import com.example.drinkerapp.pojo.DrinksByCategoryList
import com.example.drinkerapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryDrinksViewModel:ViewModel() {
    val drinksLiveData = MutableLiveData<List<DrinksByCategory>>()

    fun getDrinksByCategory(categoryName:String) {
        RetrofitInstance.api.getDrinksByCategory(categoryName).enqueue(object :Callback<DrinksByCategoryList>{
            override fun onResponse(
                call: Call<DrinksByCategoryList>,
                response: Response<DrinksByCategoryList>
            ) {
                response.body()?.let {drinkList->
                    drinksLiveData.postValue(drinkList.drinks)
                }


            }

            override fun onFailure(call: Call<DrinksByCategoryList>, t: Throwable) {
                Log.e("CategoryDrinksViewModel",t.message.toString())
            }
        })
    }

    fun observeDrinksLiveData(): LiveData<List<DrinksByCategory>> {
        return drinksLiveData
    }
}