package com.mi.imaginatoprac.di.module

import com.mi.imaginatoprac.data.account.respository.UserRepositoryImpl
import com.mi.imaginatoprac.domain.account.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAccountRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

}
