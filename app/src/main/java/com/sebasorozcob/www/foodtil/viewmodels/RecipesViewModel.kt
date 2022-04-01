package com.sebasorozcob.www.foodtil.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sebasorozcob.www.foodtil.data.DataStoreRepository
import com.sebasorozcob.www.foodtil.data.MealAndDietType
import com.sebasorozcob.www.foodtil.util.Constants.Companion.API_KEY
import com.sebasorozcob.www.foodtil.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.sebasorozcob.www.foodtil.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.sebasorozcob.www.foodtil.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_ADD_RECIPE_INFORMATION
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_API_KEY
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_DIET
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_FILL_INGREDIENTS
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_NUMBER
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_SEARCH
import com.sebasorozcob.www.foodtil.util.Constants.Companion.QUERIES_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    private lateinit var mealAndDiet: MealAndDietType

    var networkStatus = false
    var backOnline = false // If backOnline is true is because it needs to connect to internet again


    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType() =
        viewModelScope.launch(Dispatchers.IO) {

            if ((::mealAndDiet.isInitialized)) {
                dataStoreRepository.saveMealAndDietType(
                    mealAndDiet.selectedMealType,
                    mealAndDiet.selectedMealTypeId,
                    mealAndDiet.selectedDietType,
                    mealAndDiet.selectedDietTypeId
                )
            }
        }

    fun saveMealAndDietTypeTemp(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        mealAndDiet = MealAndDietType(
            mealType,
            mealTypeId,
            dietType,
            dietTypeId
        )
    }

    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        if (!(::mealAndDiet.isInitialized)) {
            queries[QUERIES_NUMBER] = DEFAULT_RECIPES_NUMBER
            queries[QUERIES_API_KEY] = API_KEY
            //queries[QUERIES_TYPE] = mealAndDiet.selectedMealType
            queries[QUERIES_TYPE] = mealType
            //queries[QUERIES_DIET] = mealAndDiet.selectedDietType
            queries[QUERIES_DIET] = dietType
            queries[QUERIES_ADD_RECIPE_INFORMATION] = "true"
            queries[QUERIES_FILL_INGREDIENTS] = "true"
        } else {
            queries[QUERIES_NUMBER] = DEFAULT_RECIPES_NUMBER
            queries[QUERIES_API_KEY] = API_KEY
            queries[QUERIES_TYPE] = mealAndDiet.selectedMealType
            //queries[QUERIES_TYPE] = mealType
            queries[QUERIES_DIET] = mealAndDiet.selectedDietType
            //queries[QUERIES_DIET] = dietType
            queries[QUERIES_ADD_RECIPE_INFORMATION] = "true"
            queries[QUERIES_FILL_INGREDIENTS] = "true"
        }

        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERIES_SEARCH] = searchQuery
        queries[QUERIES_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERIES_API_KEY] = API_KEY
        queries[QUERIES_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERIES_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection!", Toast.LENGTH_LONG).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We're back online.", Toast.LENGTH_LONG).show()
                saveBackOnline(false)
            }
        }
    }

}