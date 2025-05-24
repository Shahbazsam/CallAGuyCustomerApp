package com.example.callaguy.presentation.serviceRequest

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.model.ServiceRequestModel
import com.example.callaguy.domain.usecase.ServiceRequestUseCase
import com.example.callaguy.domain.validation.ValidateServiceRequestForm
import com.example.callaguy.presentation.subService.SubServiceUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ServiceRequestViewModel @Inject constructor(
    private val serviceRequestUseCase: ServiceRequestUseCase,
    private val validateServiceRequestForm: ValidateServiceRequestForm
) : ViewModel() {
    var requestFormState by mutableStateOf(ServiceRequestFormState())

    private val _uiState = MutableStateFlow<ServiceRequestUiState>(ServiceRequestUiState.Idle)
    val usiState = _uiState.asStateFlow()

    fun onEvent(event : ServiceRequestFormEvent) {
        when(event) {
            is ServiceRequestFormEvent.AddressChanged -> {
                requestFormState = requestFormState.copy(
                    address = event.address
                )
            }
            is ServiceRequestFormEvent.DateChanged -> {
                requestFormState = requestFormState.copy(
                    preferredDate = event.preferredDate
                )
            }
            is ServiceRequestFormEvent.InstructionChanged -> {
                requestFormState = requestFormState.copy(
                   specialInstructions = event.specialInstructions
                )
            }
            is ServiceRequestFormEvent.Submit -> {
                requestFormState = requestFormState.copy(
                   subServiceId = event.subServiceId
                )
                createServiceRequest()
            }
            is ServiceRequestFormEvent.TimeChange -> {
                requestFormState = requestFormState.copy(
                    preferredTime = event.preferredTime
                )
            }
        }
    }

    private fun createServiceRequest() {
        val check = validateServiceRequestForm.validate(requestFormState)
        if (!check){
            return
        }
        val data = ServiceRequestModel(
            subServiceId = requireNotNull(requestFormState.subServiceId),
            preferredDate = requireNotNull(requestFormState.preferredDate),
            preferredTime = requireNotNull(requestFormState.preferredTime),
            address = requestFormState.address,
            specialInstructions = requestFormState.specialInstructions
        )
        viewModelScope.launch {
            _uiState.value = ServiceRequestUiState.Loading
            delay(3000)
            when(val response = serviceRequestUseCase.createServiceRequest(data)){
                is ResultClass.Authorized<*> -> {
                    _uiState.value = ServiceRequestUiState.Success(
                        message = response.data?.message ?: " Successfully Requested "
                    )
                }
                is ResultClass.UnKnownError<*> -> {
                    _uiState.value = ServiceRequestUiState.Error(
                        code = "500",
                        message = "Something went wrong , please try again"

                    )
                }
                is ResultClass.Unauthorized<*> -> {
                    _uiState.value = ServiceRequestUiState.Error(
                        code = "401",
                        message = "Unauthorized"
                    )
                }
            }
        }
    }

}


sealed interface ServiceRequestUiState{
    data object Idle : ServiceRequestUiState
    data object Loading : ServiceRequestUiState
    data class Success(val message : String ) : ServiceRequestUiState
    data class Error(val code : String , val message : String) : ServiceRequestUiState
}