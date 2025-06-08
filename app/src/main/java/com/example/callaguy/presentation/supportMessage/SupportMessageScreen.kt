@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.callaguy.presentation.supportMessage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy.R
import com.example.callaguy.data.dto.chatScreen.SupportMessageSender
import com.example.callaguy.data.dto.supportTicket.SupportTicketStatus
import com.example.callaguy.domain.model.SupportMessagesModel
import kotlinx.coroutines.delay


@Composable
fun SupportMessageScreen(ticketId: Int, status: SupportTicketStatus) {

    val viewmodel: SupportMessageViewModel = hiltViewModel()
    val messageUiState by viewmodel.messageUiState.collectAsStateWithLifecycle()
    val sendUiState by viewmodel.sendUiState.collectAsStateWithLifecycle()
    var messageText by remember { mutableStateOf("") }

    val isOpen = status == SupportTicketStatus.OPEN
    val context = LocalContext.current

    LaunchedEffect(ticketId, isOpen) {
        if (isOpen) {
            while (true) {
                viewmodel.getMessages(ticketId)
                delay(20_000)
            }
        } else {
            viewmodel.getMessages(ticketId)
        }
    }

    LaunchedEffect(Unit) {
        viewmodel.toastEvents.collect { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAFC))
            .padding(horizontal = 16.dp)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Ticket : $ticketId",
                    fontWeight = FontWeight.SemiBold
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color(0xFFF7FAFC)
            )
        )
        Text(
            text = "Status: Open",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.DarkGray,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 8.dp, bottom = 12.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when(val state  = messageUiState){
                is MessageUiState.Error -> {
                    item {
                        ErrorState(
                            onRetry = {
                                viewmodel.getMessages(ticketId)
                            }
                        )
                    }
                }
                is MessageUiState.Success -> {
                    if (state.messages.isEmpty()) {
                        item {
                            NoMessagesYet()
                        }
                    } else {
                        items(state.messages) { message ->
                            MessageBubble(message = message)
                        }
                    }
                }
                null -> {
                    item {
                        NoMessagesYet()
                    }
                }
            }

        }

        MessageTextField(
            value = messageText,
            onValueChange = { messageText = it },
            onSendClick = {
                viewmodel.sendMessage(
                    ticketId = ticketId,
                    message = messageText
                ) {
                    messageText = ""
                }
            },
            isSending = sendUiState == SendMessageUiState.Loading
        )
    }
}


@Composable
fun MessageTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isSending: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7FAFC))
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(54.dp)
                .background(Color(0xFFE5E8EC), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.CenterStart
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = {
                    Text(
                        text = "Type a message",
                        color = Color(0xFF7A7A7A),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxSize(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = false
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onSendClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF4A90E2)),
            modifier = Modifier.height(48.dp)
        ) {
            if (isSending) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Send", color = Color.White)
            }
        }
    }
}


@Composable
fun MessageBubble(message: SupportMessagesModel) {
    val isUser = message.sender == SupportMessageSender.CUSTOMER
    val image = if (isUser) R.drawable.user else R.drawable.admin
    val bubbleColor = if (isUser) Color(0xFF4799EB) else Color(0xFFE5E8EC)
    val label = if (isUser) stringResource(R.string.user) else stringResource(R.string.support_agent)
    val rowArrangement = if (isUser) Arrangement.End else Arrangement.Start
    val columnAlignment = if (isUser) Alignment.End else Alignment.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = rowArrangement,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isUser) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(horizontalAlignment = columnAlignment) {
            Text(
                modifier = Modifier
                    .padding(bottom = 4.dp),
                text = label,
                color = Color(0xFF4799EB),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .background(bubbleColor, shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
                    .widthIn(max = 250.dp)
            ) {
                Text(
                    text = message.message,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (isUser) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
            )
        }
    }
}




@Composable
fun NoMessagesYet() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "What's the issue you're facing?\nStart a conversation now.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun ErrorState(onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Oops! Something went wrong.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Red
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}


