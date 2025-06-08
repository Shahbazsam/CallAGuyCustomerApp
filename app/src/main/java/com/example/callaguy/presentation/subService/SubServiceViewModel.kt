package com.example.callaguy.presentation.subService

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.usecase.SubServiceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SubServiceViewModel @Inject constructor(
    private val subServiceUseCase: SubServiceUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<SubServiceUiState>(SubServiceUiState.Idle)
    val uiState = _uiState.asStateFlow()


    fun getSubServices(id : Int) {
        viewModelScope.launch {
            _uiState.value = SubServiceUiState.Loading
            when(val response = subServiceUseCase.getSubServices(id)) {
                is ResultClass.Authorized<*> -> {
                    _uiState.value = SubServiceUiState.Success(response.data ?: emptyList())
                }
                is ResultClass.UnKnownError<*> -> {
                    _uiState.value = SubServiceUiState.Error(
                        code = "500",
                        message = "Something went wrong , please try again"
                    )
                }
                is ResultClass.Unauthorized<*> -> {
                    _uiState.value = SubServiceUiState.Error(
                        code = "401",
                        message = "Unauthorized"
                    )
                }
            }
        }
    }
}


sealed interface SubServiceUiState{
    data object Idle : SubServiceUiState
    data object Loading : SubServiceUiState
    data class Success(val subServices : List<SubServiceResponseDto>) : SubServiceUiState
    data class Error(val code : String , val message : String) : SubServiceUiState
}