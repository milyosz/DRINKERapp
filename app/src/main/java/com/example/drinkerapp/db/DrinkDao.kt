package com.example.drinkerapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.drinkerapp.pojo.Drink

@Dao
interface DrinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(drink:Drink)


    @Delete
    suspend fun delete(drink:Drink)

    @Query("SELECT * FROM drinkInformation")
    fun getAllDrinks():LiveData<List<Drink>>

}