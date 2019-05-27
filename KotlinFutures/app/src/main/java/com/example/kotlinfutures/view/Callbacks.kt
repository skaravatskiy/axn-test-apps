package com.example.kotlinfutures.view

interface Callback {
    fun onAuthenticated()
    fun onError(message: CharSequence?)
}
