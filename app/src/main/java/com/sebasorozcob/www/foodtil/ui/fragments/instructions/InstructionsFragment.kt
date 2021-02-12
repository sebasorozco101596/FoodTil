package com.sebasorozcob.www.foodtil.ui.fragments.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.sebasorozcob.www.foodtil.databinding.FragmentInstructionsBinding
import com.sebasorozcob.www.foodtil.models.Result
import com.sebasorozcob.www.foodtil.util.Constants

class InstructionsFragment : Fragment() {

    private var binding: FragmentInstructionsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        binding?.instructionsWebView?.webViewClient = object : WebViewClient() {

        }

        val websiteUrl: String = myBundle!!.sourceUrl
        binding?.instructionsWebView?.loadUrl(websiteUrl)

        return binding!!.root
    }
}