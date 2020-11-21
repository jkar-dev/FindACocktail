package com.jkapps.findacocktail.utils

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jkapps.findacocktail.R
import com.jkapps.findacocktail.model.Ingredient
import com.jkapps.findacocktail.view.list.CocktailListFragment

fun Fragment.showTryAgainSnackbar(@StringRes message : Int, action : () -> Unit) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_INDEFINITE).apply {
        setAction(R.string.try_again) {
            action.invoke()
        }
    }.show()
}

fun CocktailListFragment.getSpanCount() : Int {
    val isPortrait = requireContext().resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    return if (isPortrait) 2 else 3
}

fun Map<Ingredient, String>.asString() : String {
    val builder = StringBuilder()
    for ( (ingr, amount) in this) {
        builder.append("${ingr.name}: $amount \n")
    }
    builder.setLength(builder.length - 1)
    return builder.toString()
}