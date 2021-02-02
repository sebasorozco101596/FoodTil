package com.sebasorozcob.www.foodtil.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sebasorozcob.www.foodtil.R
import com.sebasorozcob.www.foodtil.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {

    private var binding: FragmentRecipesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding!!.recyclerView.showShimmer()
        return binding!!.root
    }
}