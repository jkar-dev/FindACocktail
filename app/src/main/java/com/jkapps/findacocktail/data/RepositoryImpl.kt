package com.jkapps.findacocktail.data

import com.jkapps.findacocktail.model.*
import com.jkapps.findacocktail.network.CocktailApi
import java.io.InvalidClassException

class RepositoryImpl(private val service : CocktailApi) : Repository {

    override suspend fun getIngredients() : Result<List<Ingredient>> {
        return try {
            val result = service.getIngredients()
            Result.Success(result.ingredients.sortedBy { it.name })
        } catch (e : Exception) {
            Result.Error(e.message ?: "Unknown error.")
        }
    }

    override suspend fun getCategories(): Result<List<Category>> {
        return try {
            val result = service.getCategories()
            Result.Success(result.categories)
        } catch (e : Exception) {
            Result.Error(e.message ?: "Unknown error.")
        }
    }

    override suspend fun getCocktails(entity: Entity): Result<List<Cocktail>> {
        return try {
            val result = when (entity) {
                is Ingredient -> service.getCocktailsByIngredient(entity.name)
                is Category -> service.getCocktailsByCategory(entity.name)
                else -> throw InvalidClassException(entity.javaClass.toString())
            }
            Result.Success(result.cocktails)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error.")
        }
    }

    override suspend fun getCocktailById(id: Int): Result<CocktailDetails> {
        return try {
            val result = service.getCocktailById(id)
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error.")
        }
    }
}