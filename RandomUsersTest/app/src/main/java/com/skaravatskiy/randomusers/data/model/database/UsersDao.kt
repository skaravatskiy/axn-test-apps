package com.skaravatskiy.randomusers.data.model.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(usersList: List<User>)

    @Query("SELECT * FROM user")
    fun getAll(): Flowable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    fun getById(id: Int): User

    @Query("DELETE FROM user")
    fun deleteAll()

    @Query("SELECT COUNT() FROM user")
    fun getCountItems(): Int

}
