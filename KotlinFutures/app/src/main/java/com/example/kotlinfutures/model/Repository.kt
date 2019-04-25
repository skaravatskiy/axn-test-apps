package com.example.kotlinfutures.model

interface Repository {

    suspend fun getUser(): User
}