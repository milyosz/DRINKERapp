package com.example.drinkerapp.fragments



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.drinkerapp.R
import com.example.drinkerapp.activites.CategoryDrinksActivity
import com.example.drinkerapp.activites.DrinkActivity
import com.example.drinkerapp.activites.MainActivity
import com.example.drinkerapp.adapters.CategoriesAdapter
import com.example.drinkerapp.adapters.MostPopularAdapter
import com.example.drinkerapp.databinding.FragmentHomeBinding
import com.example.drinkerapp.fragments.bottomsheet.DrinkBottomSheetFragment
import com.example.drinkerapp.pojo.Drink
import com.example.drinkerapp.pojo.DrinksByCategory
import com.example.drinkerapp.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomDrink: Drink
    private lateinit var popularItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter:CategoriesAdapter

    companion object {
        const val DRINK_ID = "com.example.drinkerapp.fragments.idDrink"
        const val DRINK_NAME = "com.example.drinkerapp.fragments.nameDrink"
        const val DRINK_THUMB = "com.example.drinkerapp.fragments.thumbDrink"
        const val CATEGORY_NAME = "com.example.drinkerapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        popularItemsAdapter = MostPopularAdapter()
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

        preparePopularItemsRecyclerView()

        viewModel.getRandomDrink()
        observerRandomDrink()
        onRandomDrinkClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()


        prepareCategoriesRecyclerView()
        viewModel.getCategoties()
        observeCategoriesLiveData()

        onCategoryClick()
        onPopularItemLongClick()

        onSearchIconClick()
        
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularItemsAdapter.onLongItemClick = {drink->
            val drinkBottomSheetFragment = DrinkBottomSheetFragment.newInstance(drink.idDrink)
            drinkBottomSheetFragment.show(childFragmentManager,"Drink Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {category ->
            val intent = Intent(activity,CategoryDrinksActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.recViewKategorie.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->
            categoriesAdapter.setCategoryList(categories)
        })
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = {drink->
            val intent = Intent(activity, DrinkActivity::class.java)
            intent.putExtra(DRINK_ID,drink.idDrink)
            intent.putExtra(DRINK_NAME,drink.strDrink)
            intent.putExtra(DRINK_THUMB,drink.strDrinkThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewDrinkPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { drinkList ->
            popularItemsAdapter.setDrinks(drinkList = drinkList as ArrayList<DrinksByCategory>)
        }
    }

    private fun onRandomDrinkClick() {
        binding.randomDrinkCard.setOnClickListener {
            val intent = Intent(activity, DrinkActivity::class.java)
            intent.putExtra(DRINK_ID,randomDrink.idDrink)
            intent.putExtra(DRINK_NAME,randomDrink.strDrink)
            intent.putExtra(DRINK_THUMB,randomDrink.strDrinkThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomDrink() {
        viewModel.observeRandomDrinkLiveData().observe(viewLifecycleOwner
        ) { drink ->
            Glide.with(this@HomeFragment)
                .load(drink!!.strDrinkThumb)
                .into(binding.randomDrink)
            this.randomDrink = drink

        }
    }


}

