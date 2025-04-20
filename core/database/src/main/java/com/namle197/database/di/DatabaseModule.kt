package com.namle197.database.di

import android.content.Context
import androidx.room.Room
import com.namle197.database.TakeHomeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesTakeHomeDatabase(
        @ApplicationContext context: Context,
    ): TakeHomeDatabase = Room.databaseBuilder(
        context,
        TakeHomeDatabase::class.java,
        "take-home-database",
    )
        .fallbackToDestructiveMigration()
        .build()
}