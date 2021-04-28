package com.mi.imaginatoprac.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.basestructure.app.BuildConfig
import com.mi.imaginatoprac.data.AppDatabase
import com.mi.imaginatoprac.data.account.respository.UserApi
import com.mi.imaginatoprac.data.account.respository.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiDBModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.DATABASE_NAME)
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .build()
    }

    @Singleton
    @Provides
    fun provideAccountApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): UserDao {
        return database.accountDao()
    }

}
