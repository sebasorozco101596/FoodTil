package com.sebasorozcob.www.foodtil.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebasorozcob.www.foodtil.models.FoodRecipe
import com.sebasorozcob.www.foodtil.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}