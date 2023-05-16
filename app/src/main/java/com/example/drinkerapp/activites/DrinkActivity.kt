package com.example.drinkerapp.activites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.drinkerapp.R
import com.example.drinkerapp.databinding.ActivityDrinkBinding
import com.example.drinkerapp.db.DrinkDatabase
import com.example.drinkerapp.fragments.HomeFragment
import com.example.drinkerapp.pojo.Drink
import com.example.drinkerapp.viewModel.DrinkViewModel
import com.example.drinkerapp.viewModel.DrinkViewModelFactory

class DrinkActivity : AppCompatActivity() {
    private lateinit var drinkId:String
    private lateinit var drinkName:String
    private lateinit var drinkThumb:String
    private lateinit var binding: ActivityDrinkBinding
    private lateinit var drinkMvvm: DrinkViewModel
    private lateinit var ytLink: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drinkDatabase = DrinkDatabase.getInstance(this)
        val viewModelFactory = DrinkViewModelFactory(drinkDatabase)
        drinkMvvm = ViewModelProvider(this,viewModelFactory)[DrinkViewModel::class.java]
//        drinkMvvm = ViewModelProviders.of(this)[DrinkViewModel::class.java]

        getDrinkInformationFromIntent()

        setInformationInViews()

        loadingCase()
        drinkMvvm.getDrinkDetail(drinkId)
        observerDrinkDetailsLiveData()

        onYoutubeImageClick()

        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.btnAdToFav.setOnClickListener {
            drinkToSave?.let {
                drinkMvvm.insertDrink(it)
                Toast.makeText(this,"Drink saved",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.imgYt.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ytLink.toString()))
            startActivity(intent)
        }
    }

    private var drinkToSave:Drink?=null
    private fun observerDrinkDetailsLiveData() {
        drinkMvvm.observerDrinkDetailLiveData().observe(this,object : Observer<Drink>{
            override fun onChanged(t: Drink?) {
                onResponseCase()
                val drink = t

                drinkToSave = drink

                binding.tvCategory.text = "Category : ${drink!!.strCategory}"
                binding.tvArea.text = ": ${drink.strAlcoholic}"
                binding.tvInstructionsSteps.text = drink.strInstructions

//                ytLink = drink.strDrinkAlternate!!
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(drinkThumb)
            .into(binding.imgDrinkDetail)
        binding.collapsingToolbar.title = drinkName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getDrinkInformationFromIntent() {
        val intent = intent
        drinkId = intent.getStringExtra(HomeFragment.DRINK_ID)!!
        drinkName = intent.getStringExtra(HomeFragment.DRINK_NAME)!!
        drinkThumb = intent.getStringExtra(HomeFragment.DRINK_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAdToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.imgYt.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAdToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.imgYt.visibility = View.INVISIBLE

    }
}