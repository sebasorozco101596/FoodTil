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

    class MyViewHolder(private val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: ExtendedIngredient) = with(binding) {
            ingredientImageView.load(BASE_IMAGE_URL + ingredient.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            ingredientName.text = ingredient.name.capitalize(Locale.ROOT)
            ingredientAmount.text = ingredient.amount.toString()
            ingredientUnit.text = ingredient.unit
            ingredientConsistency.text = ingredient.consistency
            ingredientOriginal.text = ingredient.original
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentIngredient = ingredientsList[position]
        holder.bind(currentIngredient)
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