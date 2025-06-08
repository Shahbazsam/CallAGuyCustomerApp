package com.example.callaguy.presentation.supportMessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.domain.model.SendSupportMessageModel
import com.example.callaguy.domain.model.SupportMessagesModel
import com.example.callaguy.domain.usecase.SupportMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SupportMessageViewModel@Inject constructor(
    private val useCase: SupportMessageUseCase
) : ViewModel() {

    private val _toastEvents = Channel<String>()
    val toastEvents = _toastEvents.receiveAsFlow()

    private val _messageUiState = MutableStateFlow<MessageUiState?>(null)
    val messageUiState = _messageUiState.asStateFlow()

    private val _sendUiState = MutableStateFlow<SendMessageUiState>(SendMessageUiState.Idle)
    val sendUiState = _sendUiState.asStateFlow()

    fun getMessages( id : Int) {
        viewModelScope.launch {
            when(val response = useCase.getSupportMessages(id)) {
                is ResultClass.Authorized<*> -> {
                    _messageUiState.value = MessageUiState.Success(
                        messages = response.data ?: emptyList()
                    )
                }
                is ResultClass.UnKnownError<*> -> {
                    _messageUiState.value = MessageUiState.Error(
                        code = "500",
                        message = "Something went wrong , please try again"
                    )
                    _toastEvents.send("Failed To Load Messages")
                }
                is ResultClass.Unauthorized<*> -> {
                    _messageUiState.value = MessageUiState.Error(
                        code = "401",
                        message = "Unauthorized"
                    )
                    _toastEvents.send("Session Expired")
                }
            }
        }
    }

    fun sendMessage( ticketId : Int , message : String , onSuccess : () -> Unit ) {
        val data = SendSupportMessageModel(
            ticketId = ticketId,
            message = message
        )
        viewModelScope.launch {
            _sendUiState.value = SendMessageUiState.Loading

            when(useCase.sendMessages(data)){
                is ResultClass.Authorized<*> -> {
                    getMessages(ticketId)
                    onSuccess()
                }
                is ResultClass.UnKnownError<*> -> {
                    _toastEvents.send("Session Expired")
                }
                is ResultClass.Unauthorized<*> -> {
                    _toastEvents.send("Failed to send Messages")
                }
            }
            _sendUiState.value = SendMessageUiState.Idle
        }
    }

}

sealed interface MessageUiState {
    data class Success(val messages : List <SupportMessagesModel>) : MessageUiState
    data class Error(val code : String , val message : String) : MessageUiState
}

sealed interface SendMessageUiState {
    data object Idle : SendMessageUiState
    data object Loading : SendMessageUiState
}