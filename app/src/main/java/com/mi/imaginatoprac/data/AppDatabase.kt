package com.mi.imaginatoprac.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.basestructure.app.BuildConfig
import com.mi.imaginatoprac.data.account.respository.UserDao
import com.mi.imaginatoprac.domain.account.entity.User

@Database(
    entities = [User::class],
    version = BuildConfig.VERSION_CODE
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDao(): UserDao
}
