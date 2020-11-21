package com.jkapps.findacocktail

import android.app.Application
import com.jkapps.findacocktail.data.RepositoryImpl
import com.jkapps.findacocktail.network.Service
import com.jkapps.findacocktail.view.category.CategoryViewModel
import com.jkapps.findacocktail.view.details.DetailsViewModel
import com.jkapps.findacocktail.view.ingredient.IngredientsViewModel
import com.jkapps.findacocktail.view.list.CocktailListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class CocktailApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKoin()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        val modules = module {
            single { Service.create() }
            single { RepositoryImpl(get()) }

            viewModel { IngredientsViewModel(get<RepositoryImpl>()) }
            viewModel { CategoryViewModel(get<RepositoryImpl>()) }
            viewModel { CocktailListViewModel(get<RepositoryImpl>()) }
            viewModel { DetailsViewModel(get<RepositoryImpl>()) }
        }

        startKoin {
            modules(modules)
        }
    }
}