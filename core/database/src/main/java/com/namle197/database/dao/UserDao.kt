package com.namle197.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.namle197.database.entity.UserEntity

@Dao
interface UserDao {
    @Query(
        value = """
        SELECT *
        FROM user
        ORDER BY user.id ASC
        LIMIT 20;
    """)
    fun getUserEntity(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserEntities(userEntities: List<UserEntity>)
}