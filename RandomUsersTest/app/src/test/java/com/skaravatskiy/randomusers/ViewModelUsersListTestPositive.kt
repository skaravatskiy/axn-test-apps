package com.skaravatskiy.randomusers

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.google.gson.Gson
import com.skaravatskiy.randomusers.data.model.common.Mapper
import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.data.model.poko.UsersResponse
import com.skaravatskiy.randomusers.data.repository.Repository
import com.skaravatskiy.randomusers.ui.userslist.Sort
import com.skaravatskiy.randomusers.viewmodel.UserListViewModel
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import android.arch.lifecycle.LiveData as LiveData1

@RunWith(JUnit4::class)
class ViewModelUsersListTestPositive {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<List<User>>
    @Mock
    lateinit var repository: Repository

    private lateinit var users: List<User>

    private lateinit var viewModel: UserListViewModel

    @Before
    fun setup() {
        //mocked list of users from json string as List<User>
        users = Gson().fromJson(Const.USERS, UsersResponse::class.java).run { Mapper.toUser(this.results) }
        MockitoAnnotations.initMocks(this)
        //Work with RX MainThreadSceduler
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = UserListViewModel()
        viewModel.repository = repository
    }

    @Test
    fun insertUserThenGetUser() {
        val toInsert = ArgumentMatchers.anyList<User>()
        viewModel.getData().observeForever(observer)
        viewModel.updateUser(toInsert)
        val returned = viewModel.getData().value
        Assert.assertEquals(toInsert, returned)
    }

    @Test
    fun insertUsersThenSortUsersByAge() {
        viewModel.getData().observeForever(observer)
        viewModel.updateUser(users)
        viewModel.sortBy(Sort.AGE)
        val result = viewModel.getData().value
        var status = true
        if (result != null) {
            for (i in 0 until result.size - 1) {
                if (result[i].age <= result[i + 1].age) {
                    status = true
                    continue
                } else {
                    status = false
                    break
                }
            }
        }
        Assert.assertEquals(true, status)
    }

    @Test
    fun insertUsersThenSortUsersByName() {
        viewModel.getData().observeForever(observer)
        viewModel.updateUser(users)
        viewModel.sortBy(Sort.NAME)
        val result = viewModel.getData().value
        val expected = users.sortedWith(compareBy { it.nameFirst })
        Assert.assertEquals(expected, result)
    }

    @Test
    fun insertUsersThenSortUsersByLocation() {
        viewModel.getData().observeForever(observer)
        viewModel.updateUser(users)
        viewModel.sortBy(Sort.LOCATION)
        val result = viewModel.getData().value
        val expected = users.sortedWith(compareBy { it.location })
        Assert.assertEquals(expected, result)
    }

    @Test
    fun insertUsersThenCheckResponse() {
        val toInsert = users
        val latch = CountDownLatch(1)
        `when`(repository.getUsers()).thenAnswer {
            return@thenAnswer Observable.just(toInsert)
        }
        viewModel.getData().observeForever(observer)
        viewModel.requestUsers()
        latch.await(1, TimeUnit.SECONDS)
        Assert.assertEquals(toInsert, viewModel.getData().value)
    }
}
