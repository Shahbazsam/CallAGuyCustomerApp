package com.example.callaguy.data.repository

import com.example.callaguy.data.local.ProfileDao
import com.example.callaguy.data.local.ProfileEntity
import com.example.callaguy.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val dao : ProfileDao
) : ProfileRepository {
    override suspend fun deleteProfileFromDatabase() {
        dao.deleteProfile()
    }

    override fun getProfileFromDatabase(): Flow<ProfileEntity?> {
        return dao.getProfile()
    }

    override suspend fun insertOrUpdateInDatabase(profile: ProfileEntity) {
        dao.insertOrUpdate(profile)
    }
}