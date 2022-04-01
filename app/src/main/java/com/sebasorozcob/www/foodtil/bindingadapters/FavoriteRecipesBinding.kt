package com.sebasorozcob.www.foodtil.bindingadapters

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sebasorozcob.www.foodtil.data.database.entities.FavoritesEntity
import com.sebasorozcob.www.foodtil.ui.adapters.FavoriteRecipesAdapter

class FavoriteRecipesBinding {

    companion object {

        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?,
            mAdapter: FavoriteRecipesAdapter?
        ) {
            when (view) {
                is RecyclerView -> {
                    val dataCheck = favoritesEntity.isNullOrEmpty()
                    view.isVisible = !dataCheck
                    if (!dataCheck) {
                        favoritesEntity?.let { mAdapter?.setData(it) }
                    }
                }
                else -> view.isVisible = favoritesEntity.isNullOrEmpty()
            }
        }
    }
}

