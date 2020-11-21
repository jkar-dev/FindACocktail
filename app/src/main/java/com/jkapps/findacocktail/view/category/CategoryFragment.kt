package com.jkapps.findacocktail.view.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jkapps.findacocktail.R
import com.jkapps.findacocktail.adapters.ListAdapter
import com.jkapps.findacocktail.databinding.EntityListFragmentBinding
import com.jkapps.findacocktail.model.Entity
import com.jkapps.findacocktail.utils.showTryAgainSnackbar
import com.jkapps.findacocktail.view.list.CocktailListFragment
import org.koin.android.ext.android.inject

class CategoryFragment : Fragment() {
    private val viewModel: CategoryViewModel by inject()
    private lateinit var adapter: ListAdapter
    private lateinit var bindind : EntityListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = EntityListFragmentBinding.inflate(inflater, container, false)
        return bindind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()

        viewModel.categories.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
        viewModel.showError.observe(viewLifecycleOwner, Observer {
            it.getDataIfNotHandled()?.let {
                showTryAgainSnackbar(R.string.error_message) {
                    viewModel.getCategories()
                }
            }
        })
    }

    private fun setUpRecycler() {
        initAdapter()
        bindind.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CategoryFragment.adapter
        }
    }

    private fun initAdapter() {
        adapter = ListAdapter {
            openCocktailsList(it)
        }
    }

    private fun openCocktailsList(entity : Entity) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.mainContainer, CocktailListFragment.instance(entity))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun instance() = CategoryFragment()
    }
}