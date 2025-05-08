package com.example.callaguy.domain.usecase

import android.net.Uri
import androidx.core.net.toFile
import com.example.callaguy.data.dto.profile.ProfileInfoResponseDto
import com.example.callaguy.data.local.ProfileEntity
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class ProfileUseCase(
    private val repository: ProfileRepository
) {
    fun getProfileFromDatabase() : Flow<ProfileEntity?> {
       return repository.getProfileFromDatabase()
    }
    suspend fun insertOrUpdateInDatabase( userName: String, email: String, address: String?, phone: String?) {
        repository.insertOrUpdateInDatabase(userName, email, address, phone)
    }

    suspend fun updatePictureAndSync(picture: String?, isSynced: Boolean) {
        repository.updatePictureAndSync(picture, isSynced)
    }

    suspend fun deleteProfileFromDatabase() {
        repository.deleteProfileFromDatabase()
    }

    suspend fun updateProfilePicture(image : Uri): ResultClass<Unit> {
        val file = image.toFile()
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("profile", file.name , requestBody)
        return try {
            repository.updateProfile(body)
            ResultClass.Authorized()
        }catch (e: HttpException) {
            when (e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        } catch (e: Exception) {
            ResultClass.UnKnownError()
        }
    }
    suspend fun getProfileInfo(): ResultClass<ProfileInfoResponseDto> {
        return try {
            val response = repository.getProfileInfo()
            ResultClass.Authorized(response)
        }catch (e: HttpException) {
            when (e.code()) {
                401 -> ResultClass.Unauthorized()
                else -> ResultClass.UnKnownError()
            }
        } catch (e: Exception) {
            ResultClass.UnKnownError()
        }
    }
}