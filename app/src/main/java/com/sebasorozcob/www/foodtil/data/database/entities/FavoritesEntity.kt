package com.sebasorozcob.www.foodtil.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebasorozcob.www.foodtil.models.Result
import com.sebasorozcob.www.foodtil.util.Constants.Companion.FAVORITE_RECIPES_TABLE

@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)