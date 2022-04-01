package com.sebasorozcob.www.foodtil.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sebasorozcob.www.foodtil.R
import com.sebasorozcob.www.foodtil.databinding.IngredientsRowLayoutBinding
import com.sebasorozcob.www.foodtil.models.ExtendedIngredient
import com.sebasorozcob.www.foodtil.util.Constants.Companion.BASE_IMAGE_URL
import com.sebasorozcob.www.foodtil.util.RecipesDiffUtil
import java.util.*

private const val TAG = "IngredientsAdapter"

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            IngredientsRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentIngredient = ingredientsList[position]
        with(holder.binding) {
            ingredientImageView.load(BASE_IMAGE_URL + currentIngredient.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            ingredientName.text = currentIngredient.name.capitalize(java.util.Locale.ROOT)
            ingredientAmount.text = currentIngredient.amount.toString()
            ingredientUnit.text = currentIngredient.unit
            ingredientConsistency.text = currentIngredient.consistency
            ingredientOriginal.text = currentIngredient.original
        }
    }

    override fun getItemCount(): Int {
        Log.d("IngredientsAdapter", "${ingredientsList.size}")
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        Log.d(TAG, "${newIngredients.size}")
        val ingredientsDiffUtil =
            RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}