package com.example.drinkerapp.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drinkerapp.adapters.CategoryDrinksAdapter
import com.example.drinkerapp.databinding.ActivityCategoryDinksBinding
import com.example.drinkerapp.fragments.HomeFragment
import com.example.drinkerapp.viewModel.CategoryDrinksViewModel

class CategoryDrinksActivity : AppCompatActivity() {
    lateinit var binding:ActivityCategoryDinksBinding
    lateinit var categoryDrinksViewModel:CategoryDrinksViewModel
    lateinit var categoryDrinksAdapter:CategoryDrinksAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryDinksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryDrinksViewModel = ViewModelProviders.of(this)[CategoryDrinksViewModel::class.java]

        categoryDrinksViewModel.getDrinksByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryDrinksViewModel.observeDrinksLiveData().observe(this, Observer { drinkList->

            binding.tvCategoryCount.text = drinkList.size.toString()
            categoryDrinksAdapter.setDrinksList(drinkList)



        })
    }


    private fun prepareRecyclerView() {
        categoryDrinksAdapter = CategoryDrinksAdapter()
        binding.rvDrinks.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryDrinksAdapter


        }
    }
}