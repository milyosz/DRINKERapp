package com.example.drinkerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drinkerapp.pojo.Drink
import com.example.drinkerapp.pojo.DrinkList
import com.example.drinkerapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel():ViewModel() {
    private var randomDrinkLiveData = MutableLiveData<Drink>()

    fun getRandomDrink(){
        RetrofitInstance.api.getRandomDrink().enqueue(object : Callback<DrinkList> {
            override fun onResponse(call: Call<DrinkList>, response: Response<DrinkList>) {
                if(response.body() != null){
                    val randomDrink: Drink = response.body()!!.drinks[0]
                    randomDrinkLiveData.value = randomDrink

                }else
                    return
            }
            // kiedy się nie uda
            override fun onFailure(call: Call<DrinkList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun observeRandomDrinkLiveData():LiveData<Drink>{
        return randomDrinkLiveData
    }


}