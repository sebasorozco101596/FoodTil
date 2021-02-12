package com.sebasorozcob.www.foodtil.ui.fragments.ingredients

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebasorozcob.www.foodtil.databinding.FragmentIngredientsBinding
import com.sebasorozcob.www.foodtil.models.Result
import com.sebasorozcob.www.foodtil.ui.adapters.IngredientsAdapter
import com.sebasorozcob.www.foodtil.util.Constants.Companion.RECIPE_RESULT_KEY

private const val TAG = "IngredientsFragment"
class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }
    private var binding: FragmentIngredientsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setUpRecyclerView()
        myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
            Log.d(TAG,"$it")
        }

        return binding!!.root
    }

    private fun setUpRecyclerView() {
        binding?.ingredientsRecyclerView?.adapter = mAdapter
        binding?.ingredientsRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }
}