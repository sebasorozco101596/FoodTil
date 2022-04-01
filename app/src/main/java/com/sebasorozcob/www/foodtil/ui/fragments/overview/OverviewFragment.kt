package com.sebasorozcob.www.foodtil.ui.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.sebasorozcob.www.foodtil.R
import com.sebasorozcob.www.foodtil.bindingadapters.RecipesRowBinding
import com.sebasorozcob.www.foodtil.databinding.FragmentOverviewBinding
import com.sebasorozcob.www.foodtil.models.Result
import com.sebasorozcob.www.foodtil.util.Constants.Companion.RECIPE_RESULT_KEY

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result = args!!.getParcelable<Result>(RECIPE_RESULT_KEY) as Result

        with(binding) {
            mainImageView.load(myBundle.image)
            titleTextView.text = myBundle.title
            likesTextView.text = myBundle.aggregateLikes.toString()
            timeTextView.text = myBundle.readyInMinutes.toString()
            RecipesRowBinding.parseHtml(binding.summaryTextView, myBundle.summary)

            updateColors(myBundle.vegetarian, vegetarianTextView, vegetarianImageView)
            updateColors(myBundle.vegan, veganTextView, veganImageView)
            updateColors(myBundle.cheap, cheapTextView, cheapImageView)
            updateColors(myBundle.dairyFree, dairyFreeTextView, dairyFreeImageView)
            updateColors(myBundle.glutenFree, glutenFreeTextView, glutenFreeImageView)
            updateColors(myBundle.veryHealthy, healthyTextView, healthyImageView)

            return root
        }
    }

    private fun updateColors(stateIsOn: Boolean, textView: TextView, imageView: ImageView) {
        if (stateIsOn) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}