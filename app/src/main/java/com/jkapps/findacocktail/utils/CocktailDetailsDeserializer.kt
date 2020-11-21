package com.jkapps.findacocktail.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.jkapps.findacocktail.model.CocktailDetails
import com.jkapps.findacocktail.model.Ingredient
import timber.log.Timber
import java.lang.reflect.Type


/** Response provided by API is not handy to use the standard deserializer **/
class CocktailDetailsDeserializer : JsonDeserializer<CocktailDetails> {

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ) : CocktailDetails {
        val jsonString = json.toString()
        val objString = jsonString.substring(11, jsonString.length.minus(2))
        val obj = JsonParser.parseString(objString).asJsonObject

        val recipe = mutableMapOf<Ingredient, String>()
        for (i in 1..15) {
            val ingredientStr = obj.get("strIngredient$i")
            if (!ingredientStr.isJsonNull) {
                val ingr = Ingredient(ingredientStr.asString)
                val measureStr = obj.get("strMeasure$i")
                if (measureStr.isJsonNull) recipe[ingr] = "by taste"
                else recipe[ingr] = measureStr.asString
            }
            else break
        }
        Timber.i(recipe.toString())
        return CocktailDetails(
            obj.get("idDrink").asInt,
            obj.get("strDrink").asString,
            obj.get("strDrinkThumb").asString,
            obj.get("strInstructions").asString,
            recipe
        )
    }
}