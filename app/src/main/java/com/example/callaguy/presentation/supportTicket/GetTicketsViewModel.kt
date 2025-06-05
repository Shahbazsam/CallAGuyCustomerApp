package com.example.callaguy.presentation.supportTicket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.data.dto.supportTicket.SupportTicketStatus
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.model.SupportTicketsModel
import com.example.callaguy.domain.usecase.SupportTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GetTicketsViewModel @Inject constructor(
    private val useCase: SupportTicketUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GetSupportTicketUiState>(GetSupportTicketUiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        fetchTickets()
    }

    fun fetchTickets(){
        viewModelScope.launch {
            _uiState.value = GetSupportTicketUiState.Loading

            when(val result = useCase.getSupportTickets()){
                is ResultClass.Authorized<*> -> {
                    val nonNullList = result.data ?: emptyList()

                    val ( open , resolved) = nonNullList.partition {
                        it.status == SupportTicketStatus.OPEN
                    }
                    _uiState.value = GetSupportTicketUiState.Success(
                        open = open,
                        resolved = resolved
                    )
                }
                is ResultClass.UnKnownError<*> -> {
                    _uiState.value = GetSupportTicketUiState.Error(
                        code = "500",
                        message = "Something went wrong. Please try again."
                    )
                }
                is ResultClass.Unauthorized<*> -> {
                    _uiState.value = GetSupportTicketUiState.Error(
                        code = "401",
                        message = " Unauthorized."
                    )
                }
            }
        }
    }

}


sealed interface GetSupportTicketUiState{
    object Idle : GetSupportTicketUiState
    object Loading : GetSupportTicketUiState
    data class Success(val open : List<SupportTicketsModel> , val resolved : List<SupportTicketsModel>) : GetSupportTicketUiState
    data class Error(val code : String , val message : String) : GetSupportTicketUiState
}
