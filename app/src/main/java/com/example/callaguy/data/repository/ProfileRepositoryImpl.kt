package com.example.callaguy.data.repository

import com.example.callaguy.data.dto.profile.ProfileInfoResponseDto
import com.example.callaguy.data.local.ProfileDao
import com.example.callaguy.data.local.ProfileEntity
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class ProfileRepositoryImpl(
    private val dao : ProfileDao,
    private val apiService: ApiService
) : ProfileRepository {
    override suspend fun deleteProfileFromDatabase() {
        dao.deleteProfile()
    }

    override fun getProfileFromDatabase(): Flow<ProfileEntity?> {
        return dao.getProfile()
    }

    override suspend fun insertOrUpdateInDatabase( userName: String, email: String, address: String?, phone: String?) {
        dao.updateProfileInfo(userName, email, address, phone)
    }

    override suspend fun updatePictureAndSync(picture: String?, isSynced: Boolean) {
        dao.updatePictureAndSync(picture, isSynced)
    }

    override suspend fun updateProfile(image: MultipartBody.Part) {
        apiService.updateProfilePicture(image)
    }

    override suspend fun getProfileInfo(): ProfileInfoResponseDto {
        return apiService.getProfileInfo()
    }
}