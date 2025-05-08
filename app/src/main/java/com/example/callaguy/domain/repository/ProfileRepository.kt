package com.example.callaguy.domain.repository

import com.example.callaguy.data.dto.profile.ProfileInfoResponseDto
import com.example.callaguy.data.local.ProfileEntity
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProfileRepository {
    // database
    fun getProfileFromDatabase() : Flow<ProfileEntity?>
    suspend fun insertOrUpdateInDatabase( userName: String, email: String, address: String?, phone: String?)
    suspend fun updatePictureAndSync(picture: String?, isSynced: Boolean)
    suspend fun deleteProfileFromDatabase()

    //Network
    suspend fun updateProfile( image : MultipartBody.Part)
    suspend fun getProfileInfo() : ProfileInfoResponseDto
}