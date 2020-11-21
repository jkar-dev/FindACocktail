package com.jkapps.findacocktail

import com.jkapps.findacocktail.model.Ingredient
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testImageUrl() {
        val ingr = Ingredient("Light rum")
        assertEquals("https://www.thecocktaildb.com/images/ingredients/Light%20rum-Small.png", ingr.imageUrl)
    }
}