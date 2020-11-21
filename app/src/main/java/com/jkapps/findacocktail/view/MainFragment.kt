package com.jkapps.findacocktail.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jkapps.findacocktail.R
import com.jkapps.findacocktail.adapters.PagerAdapter
import com.jkapps.findacocktail.databinding.MainFragmentBinding
import com.jkapps.findacocktail.view.category.CategoryFragment
import com.jkapps.findacocktail.view.ingredient.IngredientFragment
import timber.log.Timber

class MainFragment : Fragment() {
    private lateinit var binding : MainFragmentBinding
    private val fragments : MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("onCreate")

        fragments.apply {
            add(IngredientFragment.instance())
            add(CategoryFragment.instance())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated")

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = PagerAdapter(childFragmentManager, lifecycle).apply { setFragments(fragments) }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setText(R.string.ingredients)
                1 -> tab.setText(R.string.categories)
            }
        }.attach()
    }

    companion object {
        val instance = MainFragment()
    }
}