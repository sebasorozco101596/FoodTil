package com.sebasorozcob.www.foodtil.data

import com.sebasorozcob.www.foodtil.data.network.FoodRecipesAPI
import com.sebasorozcob.www.foodtil.models.FoodJoke
import com.sebasorozcob.www.foodtil.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesAPI: FoodRecipesAPI
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesAPI.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesAPI.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return foodRecipesAPI.getFoodJoke(apiKey)
    }

}