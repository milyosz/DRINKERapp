package com.example.drinkerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.drinkerapp.db.DrinkDatabase

class DrinkViewModelFactory(
    private val drinkDatabase: DrinkDatabase
) : ViewModelProvider.Factory {
    override fun <T:ViewModel> create(modelClass:Class<T>):T {
        return DrinkViewModel(drinkDatabase) as T
    }

}
