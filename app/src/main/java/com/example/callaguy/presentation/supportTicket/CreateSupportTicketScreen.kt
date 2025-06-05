package com.example.callaguy.presentation.supportTicket

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy.R
import com.example.callaguy.presentation.loadingScreens.ErrorScreen
import com.example.callaguy.presentation.serviceRequest.BookingConfirmationScreen
import com.example.callaguy.presentation.serviceRequest.ServiceRequestLoadingScreen

@Composable
fun CreateSupportTicketRoot(
    serviceRequestId: Int,
    onGoToTickets: () -> Unit
) {

    val viewModel: CreateSupportTicketViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when (state) {
        is CreateSupportTicketUiState.Error -> {
            ErrorScreen(
                onRetry = {
                    viewModel.onEvent(SupportTicketFormEvent.Submit(serviceRequestId))
                }
            )
        }

        CreateSupportTicketUiState.Idle -> {
            CreateSupportTicketScreen(
                serviceRequestId = serviceRequestId,
                viewModel = viewModel
            )
        }
        CreateSupportTicketUiState.Loading -> {
            ServiceRequestLoadingScreen()
        }

        CreateSupportTicketUiState.Success -> {
            BookingConfirmationScreen(
                text = " Ticket Created Successfully",
                onGoToOrdersOrTickets = onGoToTickets
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSupportTicketScreen(
    serviceRequestId: Int,
    viewModel: CreateSupportTicketViewModel
) {
    val state = viewModel.state
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAFC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.raise_a_ticket),
                    fontWeight = FontWeight.SemiBold
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color(0xFFF7FAFC)
            )
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Spacer(Modifier.height(12.dp))
            SupportTextField(
                value = state.issue,
                onValueChange = {
                    viewModel.onEvent(SupportTicketFormEvent.IssueChange(it))
                },
                placeholder = stringResource(R.string.what_s_the_issue)
            )
            state.issueError?.let {
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp , top = 12.dp),
                    text = "Field Can't be Empty *",
                    fontSize = 16.sp,
                    color = Color.Red
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, bottom = 18.dp)
                .size(width = 55.dp, height = 55.dp),
            onClick = {
                viewModel.onEvent(SupportTicketFormEvent.Submit(serviceRequestId = serviceRequestId))
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(Color(0xFF4A90E2))
        ) {
            Text(
                text = stringResource(R.string.raise_ticket),
                color = Color(0xFFFFFFFF),

            )
        }
    }
}

@Composable
fun SupportTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFE8EDF5),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .padding(horizontal = 16.dp, vertical = 16.dp),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFF4A90E2),
                        fontSize = 16.sp
                    )
                }
                innerTextField()
            }
        )

    }
}

