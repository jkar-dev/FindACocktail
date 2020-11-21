package com.jkapps.findacocktail.network.model

import com.google.gson.annotations.SerializedName
import com.jkapps.findacocktail.model.Category

data class CategoryResponse(
    @SerializedName("drinks")
    val categories : List<Category>
)