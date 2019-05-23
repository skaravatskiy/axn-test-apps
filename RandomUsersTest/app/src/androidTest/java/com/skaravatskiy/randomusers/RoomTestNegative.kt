package com.skaravatskiy.randomusers

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.google.gson.Gson
import com.skaravatskiy.randomusers.data.model.common.Mapper
import com.skaravatskiy.randomusers.data.model.database.AppDatabase
import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.data.model.database.UsersDao
import com.skaravatskiy.randomusers.data.model.poko.UsersResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class RoomTestNegative {

    private lateinit var dao: UsersDao
    private lateinit var users: List<User>
    private lateinit var USER: User

    @Before
    fun setup() {
        users = Gson().fromJson(Const.USERS, UsersResponse::class.java).run { Mapper.toUser(this.results) }
        USER = users[Random.nextInt(users.size)]
        val context = InstrumentationRegistry.getTargetContext()

        val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.daoAccess()
        dao.deleteAll()
    }

    @Test
    fun getUserByZeroId() {
        dao.insert(users)
        try {
            dao.getById(ZERO)
        } catch (expected: Throwable) {
            assertEquals(java.lang.NullPointerException::class, expected::class)
        }
    }

    companion object {
        const val ZERO = 0
    }
}
