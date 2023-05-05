package com.example.drinkerapp.fragments



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.drinkerapp.activites.DrinkActivity
import com.example.drinkerapp.databinding.FragmentHomeBinding
import com.example.drinkerapp.pojo.Drink
import com.example.drinkerapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeMvvm.getRandomDrink()
        observerRandomDrink()
        onRandomDrinkClick()

    }

    private fun onRandomDrinkClick() {
        binding.randomDrinkCard.setOnClickListener {
            val intent = Intent(activity, DrinkActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observerRandomDrink() {
        homeMvvm.observeRandomDrinkLiveData().observe(viewLifecycleOwner,object : Observer<Drink>{
            override fun onChanged(t: Drink?) {
                Glide.with(this@HomeFragment)
                    .load(t!!.strDrinkThumb)
                    .into(binding.randomDrink)
            }

        })
    }




}