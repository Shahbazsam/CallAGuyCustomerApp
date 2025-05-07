package com.example.callaguy.domain.repository

import com.example.callaguy.data.local.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    // database
    fun getProfileFromDatabase() : Flow<ProfileEntity?>
    suspend fun insertOrUpdateInDatabase(profile : ProfileEntity)
    suspend fun deleteProfileFromDatabase()
}