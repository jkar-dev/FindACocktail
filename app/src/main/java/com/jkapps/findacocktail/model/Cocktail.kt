package com.jkapps.findacocktail.model

import com.google.gson.annotations.SerializedName

data class Cocktail(
    @SerializedName("idDrink")
    val id : Int,
    @SerializedName("strDrink")
    val name : String,
    @SerializedName("strDrinkThumb")
    val image : String
)
