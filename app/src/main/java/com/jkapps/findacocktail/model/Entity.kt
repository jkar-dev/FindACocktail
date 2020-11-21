package com.jkapps.findacocktail.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


abstract class Entity : Parcelable {
    abstract val name : String
}

@Parcelize
data class Category(
    @SerializedName("strCategory")
    override val name: String
) : Entity()

@Parcelize
data class Ingredient(
    @SerializedName("strIngredient1")
    override val name : String
) : Entity()
{
    @IgnoredOnParcel
    val imageUrl : String = "https://www.thecocktaildb.com/images/ingredients/${name.replace(" ", "%20")}-Small.png"
}