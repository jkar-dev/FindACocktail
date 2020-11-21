package com.jkapps.findacocktail.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.jkapps.findacocktail.R
import com.jkapps.findacocktail.adapters.CocktailAdapter
import com.jkapps.findacocktail.databinding.CocktailListFragmentBinding
import com.jkapps.findacocktail.model.Entity
import com.jkapps.findacocktail.utils.getSpanCount
import com.jkapps.findacocktail.utils.showTryAgainSnackbar
import com.jkapps.findacocktail.view.details.DetailsFragment
import org.koin.android.ext.android.inject


class CocktailListFragment : Fragment() {
    private val viewModel : CocktailListViewModel by inject()
    private lateinit var adapter: CocktailAdapter
    private lateinit var entity : Entity
    private lateinit var binding : CocktailListFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            entity = it.getParcelable(ARG_ENTITY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CocktailListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        setUpRecycler()

        viewModel.getCocktails(entity)
        viewModel.cocktails.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
            (view.parent as ViewGroup).doOnPreDraw {
                startPostponedEnterTransition()
            }
        })
        viewModel.showError.observe(viewLifecycleOwner, Observer {
            showTryAgainSnackbar(R.string.error_message) {
                viewModel.getCocktails(entity)
            }
        })
    }

    private fun setUpRecycler() {
        initAdapter()
        binding.recycler.apply {
            adapter = this@CocktailListFragment.adapter
            val spanCount = getSpanCount()
            layoutManager = GridLayoutManager(context, spanCount)
        }
    }

    private fun initAdapter() {
        adapter = CocktailAdapter(object : CocktailAdapter.CocktailAdapterListener {
            override fun onItemClick(id: Int, url: String, sharedView: ImageView) {
                openDetails(id, url, sharedView)
            }
        })
    }

    private fun openDetails(id : Int, url : String, sharedView: ImageView) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .addSharedElement(sharedView, sharedView.transitionName)
            .replace(R.id.mainContainer, DetailsFragment.instance(id, url))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        fun instance(entity: Entity) = CocktailListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_ENTITY, entity)
            }
        }

        private const val ARG_ENTITY = "entity"
    }
}