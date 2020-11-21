package com.jkapps.findacocktail.network.model

import com.google.gson.annotations.SerializedName
import com.jkapps.findacocktail.model.Ingredient

data class IngredientResponse (
    @SerializedName("drinks")
    val ingredients : List<Ingredient>
)