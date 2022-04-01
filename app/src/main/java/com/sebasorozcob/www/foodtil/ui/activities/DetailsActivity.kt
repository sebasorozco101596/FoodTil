package com.sebasorozcob.www.foodtil.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.sebasorozcob.www.foodtil.R
import com.sebasorozcob.www.foodtil.data.database.entities.FavoritesEntity
import com.sebasorozcob.www.foodtil.databinding.ActivityDetailsBinding
import com.sebasorozcob.www.foodtil.ui.adapters.PagerAdapter
import com.sebasorozcob.www.foodtil.ui.fragments.ingredients.IngredientsFragment
import com.sebasorozcob.www.foodtil.ui.fragments.instructions.InstructionsFragment
import com.sebasorozcob.www.foodtil.ui.fragments.overview.OverviewFragment
import com.sebasorozcob.www.foodtil.util.Constants.Companion.RECIPE_RESULT_KEY
import com.sebasorozcob.www.foodtil.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DetailsActivity"

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailsBinding

    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved = false
    private var savedRecipeId = 0

    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        binding.toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = arrayListOf<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )

        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) {tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu!!.findItem(R.id.saveToFavoritesMenu)
        checkSavedRecipes(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
            R.id.saveToFavoritesMenu -> {

                if (!recipeSaved) {
                    saveToFavorites(item)
                } else {
                    removeFromFavorites(item)
                }

            }
        }

            return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this, { favoritesEntity ->
            try {
                for (savedRecipe in favoritesEntity) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, e.message.toString())
            }
        })
    }

    private fun saveToFavorites(item: MenuItem) {

        val favoritesEntity =
            FavoritesEntity(
                0,
                args.result
            )
        mainViewModel.insertFavoriteRecipes(favoritesEntity)

        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved.")

        recipeSaved = true

    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity=
            FavoritesEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)

        changeMenuItemColor(item, R.color.white)
        showSnackBar("Recipe deleted from favorites.")

        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    private fun changeMenuItemColor(
        item: MenuItem,
        color: Int
    ) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }
}