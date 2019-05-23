package com.skaravatskiy.randomusers

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.google.gson.Gson
import com.skaravatskiy.randomusers.data.model.common.Mapper
import com.skaravatskiy.randomusers.data.model.database.User
import com.skaravatskiy.randomusers.data.model.poko.UsersResponse
import com.skaravatskiy.randomusers.data.repository.Repository
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class ViewModelUsersListTestNegative {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<List<User>>
    @Mock
    lateinit var repository: Repository

    lateinit var users: List<User>

    lateinit var viewModel: UserListViewModel

    @Before
    fun setup() {
        users = Gson().fromJson(Const.USERS, UsersResponse::class.java).run { Mapper.toUser(this.results) }
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = UserListViewModel()
        viewModel.repository = repository

    }

    @Test
    fun checkMockErrorResponse() {
        val response = Throwable("Error response")
        viewModel.getData().observeForever(observer)
        Mockito.`when`(repository.getUsers()).thenAnswer {
            return@thenAnswer Observable.error<Throwable> { response }
        }
        viewModel.getData().observeForever(observer)
        repository.getUsers().test().assertError(response)
    }


}
