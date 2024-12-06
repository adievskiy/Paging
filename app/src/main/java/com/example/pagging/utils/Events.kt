package com.example.pagging.utils

class Events<out T>(private val content: T) {

    var hasBeenHandled = false


    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}