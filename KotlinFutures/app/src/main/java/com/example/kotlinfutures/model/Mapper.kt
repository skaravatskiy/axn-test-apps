package com.example.kotlinfutures.model

import com.example.kotlinfutures.model.poko.RandomUserInfo

class Mapper {
    companion object {

        fun toUser(randomUserInfo: RandomUserInfo) = User(
            randomUserInfo.name.first.capitalize(),
            randomUserInfo.name.last.capitalize(),
            randomUserInfo.dob.age,
            randomUserInfo.email,
            randomUserInfo.dob.date.substringBefore('T'),
            randomUserInfo.cell,
            randomUserInfo.location.city.capitalize(),
            randomUserInfo.gender.capitalize(),
            randomUserInfo.login.username,
            randomUserInfo.login.password
        )
    }
}