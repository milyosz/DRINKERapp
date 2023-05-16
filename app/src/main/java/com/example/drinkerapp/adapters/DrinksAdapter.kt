package com.example.drinkerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkerapp.databinding.DrinkItemBinding
import com.example.drinkerapp.pojo.Drink

class DrinksAdapter:RecyclerView.Adapter<DrinksAdapter.FavoritesDrinksAdapterViewHolder>() {

    inner class FavoritesDrinksAdapterViewHolder(val binding:DrinkItemBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Drink>(){
        override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesDrinksAdapterViewHolder {
        return FavoritesDrinksAdapterViewHolder(
            DrinkItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesDrinksAdapterViewHolder, position: Int) {
        val drink = differ.currentList[position]
        Glide.with(holder.itemView).load(drink.strDrinkThumb).into(holder.binding.imgDrink)
        holder.binding.tvDrinkName.text = drink.strDrink
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}