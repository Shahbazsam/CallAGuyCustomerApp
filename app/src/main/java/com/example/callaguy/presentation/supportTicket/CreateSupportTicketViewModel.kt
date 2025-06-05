package com.example.callaguy.presentation.supportTicket

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.domain.model.CreateSupportTicketModel
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.usecase.SupportTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateSupportTicketViewModel @Inject constructor(
    private val useCase: SupportTicketUseCase
) : ViewModel() {

    var state by mutableStateOf(SupportTicketFormState())

    private val _uiState = MutableStateFlow<CreateSupportTicketUiState>(CreateSupportTicketUiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun onEvent( event: SupportTicketFormEvent) {
        when(event) {
            is SupportTicketFormEvent.IssueChange ->  {
                state = state.copy(
                    issue = event.issue
                )
            }
            is SupportTicketFormEvent.Submit -> {
                if (state.serviceRequestId == null) {
                    state = state.copy(
                        serviceRequestId = event.serviceRequestId
                    )
                }
                submit()
            }
        }
    }

    private fun submit() {

        val hasError = state.issue.isBlank() || state.serviceRequestId == null

        if (hasError){
            state = state.copy(
                issueError = " Issue must not be empty"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = CreateSupportTicketUiState.Loading

            val data = CreateSupportTicketModel(
                issueType = state.issue,
                serviceRequestId = state.serviceRequestId!!
            )
            when(useCase.createTicket(data)) {
                is ResultClass.Authorized<*> -> {
                    _uiState.value =  CreateSupportTicketUiState.Success
                }
                is ResultClass.UnKnownError<*> -> {
                    _uiState.value = CreateSupportTicketUiState.Error(
                        code = "500",
                        message = "Something went wrong. Please try again."
                    )
                }
                is ResultClass.Unauthorized<*> -> {
                    _uiState.value = CreateSupportTicketUiState.Error(
                        code = "401",
                        message = " Unauthorized."
                    )
                }
            }
        }

    }


}

sealed interface CreateSupportTicketUiState{
    object Idle : CreateSupportTicketUiState
    object Loading : CreateSupportTicketUiState
    object Success : CreateSupportTicketUiState
    data class Error(val code : String , val message : String) : CreateSupportTicketUiState
}