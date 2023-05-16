package com.example.drinkerapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.drinkerapp.pojo.Drink

@Database(entities = [Drink::class], version = 1)
@TypeConverters(DrinkTypeConvertor::class)
abstract class DrinkDatabase:RoomDatabase() {

    abstract fun drinkDao():DrinkDao

    companion object{
        @Volatile
        var INSTANCE:DrinkDatabase?=null

        @Synchronized
        fun getInstance(context: Context):DrinkDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    DrinkDatabase::class.java,
                    "drink.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as DrinkDatabase
        }
    }
}