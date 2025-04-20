package com.namle197.database.di

import com.namle197.database.TakeHomeDatabase
import com.namle197.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun providesUserDao(
        database: TakeHomeDatabase,
    ): UserDao = database.userDao()
}