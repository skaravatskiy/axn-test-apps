package com.skaravatskiy.randomusers

import android.app.Application
import android.arch.persistence.room.Room
import com.skaravatskiy.randomusers.data.model.database.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instanceApp = this
    }

    companion object {
        private var INSTANCE: AppDatabase? = null
        private lateinit var instanceApp: Application

        fun appDatabaseInstance(): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        instanceApp,
                        AppDatabase::class.java, "users.db")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}
