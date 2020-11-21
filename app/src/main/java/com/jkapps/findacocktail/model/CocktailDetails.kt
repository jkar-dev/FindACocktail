package com.jkapps.findacocktail.model

data class CocktailDetails(
    val id: Int,
    val name: String,
    val image: String,
    val instruction : String,
    val recipe : Map<Ingredient, String>
)