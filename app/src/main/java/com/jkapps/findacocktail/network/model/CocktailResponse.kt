package com.jkapps.findacocktail.network.model

import com.google.gson.annotations.SerializedName
import com.jkapps.findacocktail.model.Cocktail

data class CocktailResponse(
    @SerializedName("drinks")
    val cocktails : List<Cocktail>
)