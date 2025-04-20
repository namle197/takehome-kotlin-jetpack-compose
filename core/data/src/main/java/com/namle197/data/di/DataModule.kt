package com.namle197.data.di

import com.namle197.data.repository.user.DefaultUserRepository
import com.namle197.data.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindsUserRepository(
        userRepository: DefaultUserRepository,
    ): UserRepository
}