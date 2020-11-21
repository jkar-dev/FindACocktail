package com.jkapps.findacocktail.network

import com.jkapps.findacocktail.model.CocktailDetails
import com.jkapps.findacocktail.network.model.CategoryResponse
import com.jkapps.findacocktail.network.model.CocktailResponse
import com.jkapps.findacocktail.network.model.IngredientResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {
    @GET("list.php?i=list")
    suspend fun getIngredients() : IngredientResponse

    @GET("list.php?c=list")
    suspend fun getCategories() : CategoryResponse

    @GET("filter.php?")
    suspend fun getCocktailsByIngredient(@Query("i") ingredient: String) : CocktailResponse

    @GET("filter.php?")
    suspend fun getCocktailsByCategory(@Query("c") ingredient: String) : CocktailResponse

    @GET("lookup.php?")
    suspend fun getCocktailById(@Query("i") id : Int) : CocktailDetails
}