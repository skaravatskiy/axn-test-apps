package com.example.kotlinfutures.model.local

interface Repository {

    suspend fun getUser(): User
}
