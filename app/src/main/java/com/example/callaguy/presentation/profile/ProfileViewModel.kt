package com.example.callaguy.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.data.local.ProfileEntity
import com.example.callaguy.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCase : ProfileUseCase
) : ViewModel() {

    private val _profileUiState : StateFlow<ProfileEntity?> = useCase
            .getProfileFromDatabase()
            .stateIn(viewModelScope , SharingStarted.WhileSubscribed(5000) , null)
    val profileUiState = _profileUiState


}