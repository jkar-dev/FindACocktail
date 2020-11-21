package com.jkapps.findacocktail.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.jkapps.findacocktail.R
import com.jkapps.findacocktail.databinding.DetailsFragmentBinding
import com.jkapps.findacocktail.model.CocktailDetails
import com.jkapps.findacocktail.utils.CircleTransformation
import com.jkapps.findacocktail.utils.asString
import com.jkapps.findacocktail.utils.showTryAgainSnackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class DetailsFragment : Fragment() {
    private val viewModel : DetailsViewModel by inject()
    private var cocktailId : Int = 0
    private lateinit var url: String
    private lateinit var binding : DetailsFragmentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(R.transition.image_shared_element_transition)
        arguments?.let {
            cocktailId = it.getInt(ARG_ID)
            url = it.getString(ARG_URL, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition(700, TimeUnit.MILLISECONDS)
        preloadImage()

        viewModel.getCocktail(cocktailId)
        viewModel.cocktail.observe(viewLifecycleOwner, Observer {
            fill(it)
        })
        viewModel.showError.observe(viewLifecycleOwner, Observer {
            showTryAgainSnackbar(R.string.error_message) {
                viewModel.getCocktail(cocktailId)
            }
        })
    }

    private fun preloadImage() {
        ViewCompat.setTransitionName(binding.image, cocktailId.toString())
        Picasso.get()
            .load(url)
            .noFade()
            .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
            .transform(CircleTransformation(10f))
            .into(binding.image, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }
                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                }
            })
    }

    private fun fill(cocktail: CocktailDetails) {
        binding.description.visibility = View.VISIBLE
        binding.tvName.text = cocktail.name
        binding.tvRecipe.text = cocktail.recipe.asString()
        binding.tvInstruction.text = cocktail.instruction
    }


    companion object {
        fun instance(id : Int, url : String) = DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                    putString(ARG_URL, url)
                }

            }
        private const val ARG_ID = "cocktail id"
        private const val ARG_URL = "cocktail's image url"
    }
}