package com.sebasorozcob.www.foodtil.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_ADD_RECIPE_INFORMATION
import com.sebasorozcob.www.foodtil.util.Constants.Companion.API_KEY
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_API_KEY
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_DIET
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_FILL_INGREDIENTS
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_NUMBER
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERIES_NUMBER] = "50"
        queries[QUERIES_API_KEY] = API_KEY
        queries[QUERIES_TYPE] = "snacks"
        queries[QUERIES_DIET] = "vegan"
        queries[QUERIES_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERIES_FILL_INGREDIENTS] = "true"

        return queries
    }

}