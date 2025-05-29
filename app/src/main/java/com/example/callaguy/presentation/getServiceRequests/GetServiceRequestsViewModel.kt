package com.example.callaguy.presentation.getServiceRequests

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.domain.model.GetServiceRequestModel
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.model.ServiceRequestStatusModel
import com.example.callaguy.domain.usecase.GetServiceRequestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetServiceRequestsViewModel @Inject constructor(
    private val useCase: GetServiceRequestUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<GetServiceRequestsUiState>(GetServiceRequestsUiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        fetchOrders()
    }

    fun fetchOrders() {
        viewModelScope.launch {
            _uiState.value = GetServiceRequestsUiState.Loading
            when(val response = useCase.getServiceRequest()) {
                is ResultClass.Authorized<*> -> {
                    val nonNullList = response.data?.filterNotNull() ?: emptyList()

                    val onGoingStatuses = setOf(
                        ServiceRequestStatusModel.REQUESTED,
                        ServiceRequestStatusModel.ACCEPTED
                    )
                    val ( onGoing , past ) = nonNullList.partition {
                        it.status in onGoingStatuses
                    }
                    _uiState.value = GetServiceRequestsUiState.Success(onGoing, past)
                }
                is ResultClass.UnKnownError<*> -> {
                    _uiState.value = GetServiceRequestsUiState.Error(
                        code = "500",
                        message = "Something went wrong. Please try again."
                    )
                }
                is ResultClass.Unauthorized<*> -> {
                    _uiState.value = GetServiceRequestsUiState.Error(
                        code = "401",
                        message = " Unauthorized."
                    )
                }
            }
        }
    }

}



sealed interface GetServiceRequestsUiState {
    object Idle : GetServiceRequestsUiState
    object Loading : GetServiceRequestsUiState
    data class Success(
        val onGoing : List<GetServiceRequestModel>,
        val past : List<GetServiceRequestModel>
    ) : GetServiceRequestsUiState
    data class Error(val code : String , val message : String) : GetServiceRequestsUiState
}