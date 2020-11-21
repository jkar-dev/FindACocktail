package com.jkapps.findacocktail.utils

class Event<out T>(private val data : T) {
    private var hasBeenHandled = false

    fun getDataIfNotHandled() : T? {
        return if (hasBeenHandled) {
            null
        }
        else {
            hasBeenHandled = true
            data
        }
    }
}