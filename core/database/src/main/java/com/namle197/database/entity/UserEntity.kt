package com.namle197.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String
)