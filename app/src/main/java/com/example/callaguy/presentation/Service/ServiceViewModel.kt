package com.example.callaguy.presentation.Service

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.data.dto.service.response.ServiceResponseDto
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.usecase.ServiceUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val serviceUseCases: ServiceUseCases
)  : ViewModel() {

    private val _uiState = MutableStateFlow<ServiceUiState>(ServiceUiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        fetchServices()
    }

    fun fetchServices() {
        viewModelScope.launch {
            _uiState.value = ServiceUiState.Loading
            when(val result = serviceUseCases.getServices()) {
                is ResultClass.Authorized<*> -> {
                    _uiState.value = ServiceUiState.success(result.data ?: emptyList())
                }
                is ResultClass.UnKnownError<*> -> {
                    _uiState.value = ServiceUiState.Error(
                        code = "500",
                        message = "Something went wrong. Please try again."
                    )
                }
                is ResultClass.Unauthorized<*> -> {
                    _uiState.value = ServiceUiState.Error(
                        code = "401",
                        message = " Unauthorized."
                    )
                }
            }
        }
    }
}

sealed interface ServiceUiState{
    object Idle : ServiceUiState
    object Loading : ServiceUiState
    data class success(val services : List<ServiceResponseDto>) : ServiceUiState
    data class Error(val code : String , val message : String) : ServiceUiState
}