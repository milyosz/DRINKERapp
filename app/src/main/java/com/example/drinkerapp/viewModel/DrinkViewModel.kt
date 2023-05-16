package com.example.drinkerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkerapp.db.DrinkDatabase
import com.example.drinkerapp.pojo.Drink
import com.example.drinkerapp.pojo.DrinkList
import com.example.drinkerapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrinkViewModel(
    val drinkDatabase: DrinkDatabase
): ViewModel() {
    private var drinkDetailsLiveData = MutableLiveData<Drink>()

    fun getDrinkDetail(id:String){
        RetrofitInstance.api.getDrinkDetails(id).enqueue(object : Callback<DrinkList>{
            override fun onResponse(call: Call<DrinkList>, response: Response<DrinkList>) {
                if (response.body()!=null){
                    drinkDetailsLiveData.value = response.body()!!.drinks[0]
                }else
                    return
            }

            override fun onFailure(call: Call<DrinkList>, t: Throwable) {
                Log.d("MealActivity",t.message.toString())
            }
        })
    }

    fun observerDrinkDetailLiveData(): LiveData<Drink> {
        return drinkDetailsLiveData
    }

    fun insertDrink(drink: Drink){
        viewModelScope.launch {
            drinkDatabase.drinkDao().update(drink)
        }
    }




}