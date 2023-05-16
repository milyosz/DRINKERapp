package com.example.drinkerapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drinkerapp.db.DrinkDatabase
import com.example.drinkerapp.pojo.*
import com.example.drinkerapp.retrofit.KRetrofitInstance
import com.example.drinkerapp.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val drinkDatabase: DrinkDatabase
):ViewModel() {
    private var randomDrinkLiveData = MutableLiveData<Drink>()
    private var popularItemsLiveData = MutableLiveData<List<DrinksByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritesDrinksLiveData = drinkDatabase.drinkDao().getAllDrinks()
    private var bottomSheetLiveData = MutableLiveData<Drink>()
    private val searchedDrinksLiveData = MutableLiveData<List<Drink>>()

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

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Ordinary_Drink").enqueue(object : Callback<DrinksByCategoryList>{
            override fun onResponse(call: Call<DrinksByCategoryList>, response: Response<DrinksByCategoryList>) {
                if(response.body() != null)
                    popularItemsLiveData.value = response.body()!!.drinks
            }

            override fun onFailure(call: Call<DrinksByCategoryList>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun getCategoties(){
        KRetrofitInstance.apii.getCategories().enqueue(object:Callback <CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {

                response.body()?.let { categoryList ->
                    categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel", t.message.toString())
            }
        })
    }

    fun getMealById(id:String){
        RetrofitInstance.api.getDrinkDetails(id).enqueue(object : Callback<DrinkList>{
            override fun onResponse(call: Call<DrinkList>, response: Response<DrinkList>) {
                val drink = response.body()?.drinks?.first()
                drink?.let {drink->
                    bottomSheetLiveData.postValue(drink)
                }
            }

            override fun onFailure(call: Call<DrinkList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }
        })
    }

    fun deleteDrink(drink: Drink){
        viewModelScope.launch {
            drinkDatabase.drinkDao().delete(drink)
        }
    }

    fun insertDrink(drink: Drink){
        viewModelScope.launch {
            drinkDatabase.drinkDao().update(drink)
        }
    }

    fun searchDrinks(searchQuery: String)= RetrofitInstance.api.searchDrinks(searchQuery).enqueue(
        object : Callback<DrinkList>{
            override fun onResponse(call: Call<DrinkList>, response: Response<DrinkList>) {
                val drinkList = response.body()?.drinks
                drinkList?.let{
                    searchedDrinksLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<DrinkList>, t: Throwable) {
                Log.e("HomeViewModel",t.message.toString())
            }
        }
    )

    fun observeSearchedDrinksLiveData() : LiveData<List<Drink>> = searchedDrinksLiveData

    fun observeRandomDrinkLiveData():LiveData<Drink>{
        return randomDrinkLiveData
    }

    fun observePopularItemsLiveData():LiveData<List<DrinksByCategory>>{
        return popularItemsLiveData
    }




    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavoritesDrinksLiveData():LiveData<List<Drink>>{
        return favoritesDrinksLiveData
    }

    fun observeBottomSheetDrink():LiveData<Drink> = bottomSheetLiveData
}