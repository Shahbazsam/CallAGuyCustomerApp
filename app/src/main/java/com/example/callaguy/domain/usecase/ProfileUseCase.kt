package com.example.callaguy.domain.usecase

import com.example.callaguy.data.local.ProfileEntity
import com.example.callaguy.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileUseCase(
    private val repository: ProfileRepository
) {
    fun getProfileFromDatabase() : Flow<ProfileEntity?> {
       return repository.getProfileFromDatabase()
    }
    suspend fun insertOrUpdateInDatabase(profile : ProfileEntity) {
        repository.insertOrUpdateInDatabase(profile)
    }
    suspend fun deleteProfileFromDatabase() {
        repository.deleteProfileFromDatabase()
    }
}