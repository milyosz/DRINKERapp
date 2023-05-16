package com.example.drinkerapp.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.drinkerapp.activites.DrinkActivity
import com.example.drinkerapp.activites.MainActivity
import com.example.drinkerapp.databinding.FragmentDrinkBottomSheetBinding
import com.example.drinkerapp.fragments.HomeFragment
import com.example.drinkerapp.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val DRINK_ID = "param1"



class DrinkBottomSheetFragment : BottomSheetDialogFragment() {

    private var drinkID: String? = null

    private lateinit var binding: FragmentDrinkBottomSheetBinding

    private lateinit var viewModel:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            drinkID = it.getString(DRINK_ID)

        }

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrinkBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drinkID?.let {
            viewModel.getMealById(it)
        }
        observeBottomSheetDrink()

        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if (drinkName!=null && drinkThumb != null){
                val intent = Intent(activity,DrinkActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.DRINK_ID,drinkID)
                    putExtra(HomeFragment.DRINK_NAME,drinkName)
                    putExtra(HomeFragment.DRINK_THUMB,drinkThumb)
                }
                startActivity(intent)

            }

        }
    }


    private var drinkName:String?=null
    private var drinkThumb:String?=null
    private fun observeBottomSheetDrink() {
        viewModel.observeBottomSheetDrink().observe(viewLifecycleOwner, Observer { drink->
            Glide.with(this).load(drink.strDrinkThumb).into(binding.imgBottomSheet)
            binding.tvBottomSheetArea.text = drink.strAlcoholic
            binding.tvBottomSheetCategory.text = drink.strCategory
            binding.tvBottomSheetDrinkName.text = drink.strDrink

            drinkName = drink.strDrink
            drinkThumb = drink.strDrinkThumb

        })
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            DrinkBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(DRINK_ID, param1)

                }
            }
    }
}