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
import java.util.concurrent.TimeUnit
import kotlin.random.Random


@RunWith(AndroidJUnit4::class)
class RoomTestPositive {

    private lateinit var dao: UsersDao
    private lateinit var users: List<User>
    private lateinit var USER: User

    @Before
    fun setup() {
        users = Gson().fromJson(Const.USERS, UsersResponse::class.java).run { Mapper.toUser(this.results) }
        USER = users[Random.nextInt(20)]
        val context = InstrumentationRegistry.getTargetContext()

        val database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.daoAccess()
        dao.deleteAll()
    }

    @Test
    fun addUsersListThenClearUsersList() {
        dao.insert(users)

        dao.deleteAll()
        dao.getAll()
            .test()
            .assertEmpty()
    }

    @Test
    fun insertEmptyList() {
        val usersList: MutableList<User> = mutableListOf()
        dao.insert(usersList)

        dao.getAll()
            .test()
            .assertEmpty()
    }

    @Test
    fun insertUsersListThenGetList() {
        val toInsert = users
        dao.insert(toInsert)
        dao.getAll()
            .test()
            .awaitDone(WAIT_SEC, TimeUnit.SECONDS)
            .assertValue(toInsert)
    }

    @Test
    fun insertUserThenClearDB() {
        dao.insertItem(USER)
        dao.deleteAll()
        dao.getAll()
            .test()
            .assertEmpty()
    }

    @Test
    fun insertUserThenGetUser() {
        dao.insertItem(USER)
        assertEquals(USER, dao.getById(FIRST_USER))
    }

    companion object {
        const val FIRST_USER = 1
        const val WAIT_SEC: Long = 1
    }
}
