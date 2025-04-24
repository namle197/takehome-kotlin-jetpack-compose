package com.namle197.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.namle197.database.dao.UserDao
import com.namle197.database.entity.UserEntity

// Database for TakeHome
@Database(entities = [UserEntity::class], version = 1)
abstract class TakeHomeDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}