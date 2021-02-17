package com.sebasorozcob.www.foodtil.ui.adapters

import android.annotation.SuppressLint
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sebasorozcob.www.foodtil.R
import com.sebasorozcob.www.foodtil.data.database.entities.FavoritesEntity
import com.sebasorozcob.www.foodtil.databinding.FavoriteRecipesRowLayoutBinding
import com.sebasorozcob.www.foodtil.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.sebasorozcob.www.foodtil.util.RecipesDiffUtil
import com.sebasorozcob.www.foodtil.viewmodels.MainViewModel

class FavoriteRecipesAdapter(
    private val requireActivity: FragmentActivity,
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoritesEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    private var multiSelection = false
    private lateinit var mActionMode: ActionMode
    private lateinit var rootView: View


    inner class MyViewHolder(val binding: FavoriteRecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = FavoriteRecipesRowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = favoriteRecipes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        myViewHolders.add(holder)
        rootView = holder.itemView.rootView

        val currentRecipe = favoriteRecipes[position]
        with(holder) {
            with(currentRecipe) {
                binding.favoritesEntity = this
                binding.executePendingBindings()

                /**
                 * Single Click Listener
                 */
                binding.favoriteRecipesRowLayout.setOnClickListener {

                    if (multiSelection) {
                        applySelection(holder, this)
                    } else {
                        val action =
                            FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                                result
                            )
                        it.findNavController().navigate(action)
                    }
                }

                /**
                 * Long Click Listener
                 */
                binding.favoriteRecipesRowLayout.setOnLongClickListener {
                    if (!multiSelection) {
                        multiSelection = true
                        requireActivity.startActionMode(this@FavoriteRecipesAdapter)
                        applySelection(holder, this)
                        true
                    } else {
                        multiSelection = false
                        false
                    }
                }
            }
        }
    }

    private fun applyActionModeTitle() {
        when (selectedRecipes.size) {
            0 -> {
                mActionMode.finish()
            }
            1 -> {
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEntity) {
        if (selectedRecipes.contains(currentRecipe)) {
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(
        holder: MyViewHolder,
        backgroundColor: Int,
        strokeColor: Int
    ) {
        with(holder) {
            binding.favoriteRecipesRowLayout.setBackgroundColor(
                ContextCompat.getColor(
                    requireActivity,
                    backgroundColor
                )
            )
            binding.favoriteRowCardView.strokeWidth = 5
            binding.favoriteRowCardView.strokeColor =
                ContextCompat.getColor(
                    requireActivity,
                    strokeColor
                )
        }
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.deleteFavoriteRecipeMenu) {
            selectedRecipes.forEach {
                mainViewModel.deleteFavoriteRecipe(it)
            }
            showSnackBar("${selectedRecipes.size} Recipe/s removed.")

            multiSelection = false
            selectedRecipes.clear()
            mode?.finish()
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }


    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }

    fun setData(newFavoriteRecipes: List<FavoritesEntity>) {
        val favoriteRecipesDiffUtil =
            RecipesDiffUtil(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    @SuppressLint("ShowToast")
    private fun showSnackBar(message: String) {
        Snackbar.make(
            rootView,message, Snackbar.LENGTH_SHORT
        ).setAction("Okay"){}
            .show()
    }

    /**
     * If i changed the viewPager from favorites to joke or another the mActionMode is gonna close
     */
    fun clearContextualActionMode() {
        if (this::mActionMode.isInitialized){
            mActionMode.finish()
        }
    }
}