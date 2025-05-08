package com.example.callaguy.presentation.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.data.dto.profile.ProfileInfoResponseDto
import com.example.callaguy.data.dto.service.response.ServiceResponseDto
import com.example.callaguy.data.local.ProfileEntity
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.usecase.ProfileUseCase
import com.example.callaguy.presentation.Service.ServiceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase : ProfileUseCase
) : ViewModel() {
    val profileUiState : StateFlow<ProfileEntity?> = useCase
            .getProfileFromDatabase()
            .stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000) , null)

    private val _profileNetworkState = MutableStateFlow<ProfileNetworkState>(ProfileNetworkState.Idle)
    val profileNetworkState = _profileNetworkState.asStateFlow()

    private val _profilePictureState = MutableStateFlow<UpdateProfileImage>(UpdateProfileImage.Idle)
    val profilePictureState = _profilePictureState.asStateFlow()

    fun getProfileInfo() {
        viewModelScope.launch {
            _profileNetworkState.value = ProfileNetworkState.Loading
            when(val result = useCase.getProfileInfo()) {
                is ResultClass.Authorized<*> -> {
                    val it = result.data ?: return@launch
                    useCase.insertOrUpdateInDatabase(
                       userName =   it.userName , email = it.email , phone = it.phone  , address =it.address
                    )
                }
                is ResultClass.UnKnownError<*> -> {
                    _profileNetworkState.value = ProfileNetworkState.Error(
                        code = "500",
                        message = "Something went wrong. Please try again."
                    )
                }
                is ResultClass.Unauthorized<*> ->{
                    _profileNetworkState.value = ProfileNetworkState.Error(
                        code = "500",
                        message = "Something went wrong. Please try again."
                    )
                }
            }
        }
    }

    fun updateProfilePhoto (image : Uri) {
        viewModelScope.launch {
            _profilePictureState.value = UpdateProfileImage.Loading
                when(val result = useCase.updateProfilePicture(image)) {
                    is ResultClass.Authorized<*> -> {
                        try {
                            useCase.updatePictureAndSync(image.toString() , isSynced = true)
                            _profilePictureState.value = UpdateProfileImage.Success
                        }catch (e : Exception) {
                            _profilePictureState.value = UpdateProfileImage.Error(
                                message = e.message ?: "Profile Update Failed"
                            )
                        }
                    }
                    is ResultClass.UnKnownError<*> -> {
                        _profilePictureState.value = UpdateProfileImage.Error(
                            message = "Profile Update Failed"
                        )
                    }
                    is ResultClass.Unauthorized<*> ->{
                        _profilePictureState.value = UpdateProfileImage.Error(
                            "Unauthorized to Update Profile Picture"
                        )
                    }
                }
            }
        }
}

sealed interface UpdateProfileImage{
    object Idle : UpdateProfileImage
    object Loading : UpdateProfileImage
    object Success : UpdateProfileImage
    data class Error(val message : String) : UpdateProfileImage
}


sealed interface ProfileNetworkState{
    object Idle : ProfileNetworkState
    object Loading : ProfileNetworkState
    data class success(val profileInfo : ProfileInfoResponseDto) : ProfileNetworkState
    data class Error(val code : String , val message : String) : ProfileNetworkState
}