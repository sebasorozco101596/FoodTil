package com.sebasorozcob.www.foodtil.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebasorozcob.www.foodtil.viewmodels.MainViewModel
import com.sebasorozcob.www.foodtil.databinding.FragmentRecipesBinding
import com.sebasorozcob.www.foodtil.ui.adapters.RecipesAdapter
import com.sebasorozcob.www.foodtil.util.Constants.Companion.API_KEY
import com.sebasorozcob.www.foodtil.util.NetworkResult
import com.sebasorozcob.www.foodtil.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val recipesViewModel: RecipesViewModel by activityViewModels()
    private val mAdapter by lazy { RecipesAdapter() }
    private var binding: FragmentRecipesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesBinding.inflate(inflater, container, false)

        //mainViewModel =  ViewModelProvider(this).get(MainViewModel::class.java)
        setupRecyclerView()
        requestApiData()

        return binding!!.root
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    Log.d("RecipesFragment",response.toString())
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }



    private fun setupRecyclerView() {
        binding!!.recyclerView.adapter = mAdapter
        binding!!.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding!!.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding!!.recyclerView.hideShimmer()
    }
}