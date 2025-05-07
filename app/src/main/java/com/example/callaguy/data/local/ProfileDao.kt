package com.example.callaguy.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile Where id = 1 LIMIT 1")
    fun getProfile() : Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(profile : ProfileEntity)

    @Query("DELETE FROM profile")
    suspend fun deleteProfile()
}