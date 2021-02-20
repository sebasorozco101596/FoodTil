package com.sebasorozcob.www.foodtil.ui.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.sebasorozcob.www.foodtil.R
import com.sebasorozcob.www.foodtil.databinding.FragmentOverviewBinding
import com.sebasorozcob.www.foodtil.models.Result
import com.sebasorozcob.www.foodtil.util.Constants.Companion.RECIPE_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private var binding: FragmentOverviewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        with(binding!!) {
            mainImageView.load(myBundle?.image)
            titleTextView.text = myBundle?.title
            likesTextView.text = myBundle?.aggregateLikes.toString()
            timeTextView.text = myBundle?.readyInMinutes.toString()
            myBundle?.summary.let {
                val summary = Jsoup.parse(it).text()
                summaryTextView.text = summary
            }

            if (myBundle?.vegetarian == true) {
                vegetarianImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                vegetarianTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }

            if (myBundle?.vegan == true) {
                veganImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                veganTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }

            if (myBundle?.glutenFree == true) {
                glutenFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                glutenFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }

            if (myBundle?.dairyFree == true) {
                dairyFreeImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                dairyFreeTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }

            if (myBundle?.veryHealthy == true) {
                healthyImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                healthyTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }

            if (myBundle?.cheap == true) {
                cheapImageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
                cheapTextView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
        }


        return binding!!.root
    }
}