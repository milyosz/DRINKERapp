package com.example.drinkerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkerapp.databinding.DrinkItemBinding
import com.example.drinkerapp.pojo.DrinksByCategory

class CategoryDrinksAdapter : RecyclerView.Adapter<CategoryDrinksAdapter.CategoryDrinksViewModel>() {
    private var drinkList = ArrayList<DrinksByCategory>()

    fun setDrinksList(drinkList: List<DrinksByCategory>){
        this.drinkList = drinkList as ArrayList<DrinksByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryDrinksViewModel(val binding:DrinkItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryDrinksViewModel {
        return CategoryDrinksViewModel(
            DrinkItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryDrinksViewModel, position: Int) {
        Glide.with(holder.itemView).load(drinkList[position].strDrinkThumb).into(holder.binding.imgDrink)
        holder.binding.tvDrinkName.text = drinkList[position].strDrink
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }
}