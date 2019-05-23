package com.skaravatskiy.randomusers.data.model.common

import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.data.model.poko.RandomUserInfo

class Mapper {
    companion object {
        fun toUser(randomUsersInfo: List<RandomUserInfo>): List<User> {
            val result = mutableListOf<User>()
            randomUsersInfo.forEach {
                result.add(User(
                    it.name.first.capitalize(),
                    it.name.last.capitalize(),
                    it.dob.age,
                    it.email,
                    it.dob.date.substringBefore('T'),
                    it.cell,
                    it.location.city.capitalize(),
                    it.gender.capitalize(),
                    it.picture.medium
                ))
            }
            return result
        }
    }
}
