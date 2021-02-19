package com.sebasorozcob.www.foodtil.data.database.entities

import android.widget.TextView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebasorozcob.www.foodtil.models.FoodJoke
import com.sebasorozcob.www.foodtil.util.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}