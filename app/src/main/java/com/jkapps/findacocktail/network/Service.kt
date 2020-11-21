package com.jkapps.findacocktail.network

import com.google.gson.GsonBuilder
import com.jkapps.findacocktail.model.CocktailDetails
import com.jkapps.findacocktail.utils.CocktailDetailsDeserializer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    fun create(): CocktailApi {
        val gson = GsonBuilder().apply {
            registerTypeAdapter(CocktailDetails::class.java, CocktailDetailsDeserializer())
        }.create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(CocktailApi::class.java)
    }
}