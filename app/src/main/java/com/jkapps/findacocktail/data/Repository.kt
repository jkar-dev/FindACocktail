package com.jkapps.findacocktail.data

import com.jkapps.findacocktail.model.*

interface Repository {
    suspend fun getIngredients() : Result<List<Ingredient>>
    suspend fun getCategories() : Result<List<Category>>
    suspend fun getCocktailById(id: Int) : Result<CocktailDetails>
    suspend fun getCocktails(entity: Entity) : Result<List<Cocktail>>
}