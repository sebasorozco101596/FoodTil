package com.sebasorozcob.www.foodtil.util

class Constants {

    companion object {

//        const val API_KEY = "d657f0f1c2f445bba13c7fabbd3f431f"
        const val API_KEY = "fcd965c6e9c44e2e9c6741c909a51007"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"
        const val BASE_URL = "https://api.spoonacular.com"

        const val RECIPE_RESULT_KEY = "recipeBundle"

        // API Queries Keys
        const val QUERIES_SEARCH = "query"
        const val QUERIES_NUMBER = "number"
        const val QUERIES_API_KEY = "apiKey"
        const val QUERIES_TYPE = "type"
        const val QUERIES_DIET = "diet"
        const val QUERIES_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERIES_FILL_INGREDIENTS = "fillIngredients"

        // ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
        const val FAVORITE_RECIPES_TABLE = "favorite_recipes_table"
        const val FOOD_JOKE_TABLE = "food_joke_table"

        // Bottom sheet and preferences
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"

        const val PREFERENCES_NAME = "foodtil preferences"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

    }

}