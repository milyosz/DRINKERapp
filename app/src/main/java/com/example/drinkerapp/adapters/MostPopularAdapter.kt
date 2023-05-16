package com.example.drinkerapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drinkerapp.databinding.PopularItemsBinding
import com.example.drinkerapp.pojo.DrinksByCategory

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularDrinkViewHolder>() {
    lateinit var onItemClick:((DrinksByCategory) -> Unit)
    private var drinkList = ArrayList<DrinksByCategory>()

    var onLongItemClick:((DrinksByCategory)->Unit)?=null
    fun setDrinks(drinkList:ArrayList<DrinksByCategory>){
        this.drinkList = drinkList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularDrinkViewHolder {
        return PopularDrinkViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: PopularDrinkViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(drinkList[position].strDrinkThumb)
            .into(holder.binding.imgPopulardrinkItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(drinkList[position])
        }

        holder.itemView.setOnClickListener {
            onLongItemClick?.invoke(drinkList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    class PopularDrinkViewHolder( val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)
}