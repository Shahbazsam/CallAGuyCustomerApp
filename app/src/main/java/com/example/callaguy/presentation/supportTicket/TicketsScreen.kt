package com.example.callaguy.presentation.supportTicket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.callaguy.R
import com.example.callaguy.data.dto.supportTicket.SupportTicketStatus
import com.example.callaguy.domain.model.SupportTicketsModel
import com.example.callaguy.presentation.getServiceRequests.OrderOrTicketToggleButtons
import com.example.callaguy.presentation.loadingScreens.ErrorScreen
import com.example.callaguy.presentation.serviceRequest.ServiceRequestLoadingScreen
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun TicketsScreenRoot(onCardClick: (Int) -> Unit) {

    val viewmodel: GetTicketsViewModel = viewModel()
    val state by viewmodel.uiState.collectAsStateWithLifecycle()

    when (state) {
        is GetSupportTicketUiState.Error -> {
            ErrorScreen {
                viewmodel.fetchTickets()
            }
        }

        GetSupportTicketUiState.Idle -> Unit
        GetSupportTicketUiState.Loading -> {
            ServiceRequestLoadingScreen()
        }

        is GetSupportTicketUiState.Success -> {
            TicketsScreen(
                open = (state as GetSupportTicketUiState.Success).open,
                resolved = (state as GetSupportTicketUiState.Success).resolved,
                onCardClick = onCardClick
            )
        }
    }
}

@Composable
fun TicketsScreen(
    open: List<SupportTicketsModel>,
    resolved: List<SupportTicketsModel>,
    onCardClick: (Int) -> Unit
) {
    var openOrResolved by remember { mutableStateOf(false) }
    val currentTickets = if (!openOrResolved) open else resolved
    Column(
        modifier = Modifier
            .background(Color(0xFFF7FAFC))
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        OrderOrTicketToggleButtons(
            leftText = stringResource(R.string.open),
            rightText = stringResource(R.string.resolved),
            upcomingOrPast = openOrResolved
        ) {
            openOrResolved = it
        }
        Spacer(Modifier.height(8.dp))

        if (currentTickets.isNotEmpty()) {
            TicketList(
                tickets = currentTickets,
                onCardClick = onCardClick
            )
        } else {
            NoTicketYet()
        }
    }
}

@Composable
fun TicketList(
    tickets : List<SupportTicketsModel>,
    onCardClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(tickets) { ticket ->
            TicketCard(
                ticket = ticket,
                onCardClick = onCardClick
            )
        }
    }
}

@Composable
fun TicketCard(
    ticket: SupportTicketsModel,
    onCardClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7FAFC)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = ticket.status.toString(),
                color = if (ticket.status == SupportTicketStatus.OPEN) Color(0xFF4A739C) else Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = ticket.issueType,
                maxLines = 2,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Created on: ${ticket.createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a"))}",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(6.dp),
                onClick = {
                    onCardClick(ticket.ticketId)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE8EDF5),
                    contentColor = Color(0xFF777777)
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = null
            ) {
                Text(" Chat with Customer Care Agent ", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun NoTicketYet() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 60.dp)
            .background(Color(0xFFF7FAFC)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.drawable.no_ticket),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .size(400.dp)
                .padding(bottom = 24.dp)
        )

        Text(
            text = stringResource(R.string.no_tickets_yet),
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4A739C)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.you_didn_t_raised_any_ticket_yet),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF777777),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Preview(modifier: Modifier = Modifier) {
    val fakeDate = LocalDateTime.of(2025, 6, 1, 10, 30)

    val fakeOpenTickets = listOf(
        SupportTicketsModel(
            ticketId = 3,
            customerId = 1,
            serviceRequestId = 101,
            issueType = "App crash on submit",
            status = SupportTicketStatus.OPEN,
            createdAt = fakeDate
        ),
        SupportTicketsModel(
            ticketId = 4,
            customerId = 2,
            serviceRequestId = 102,
            issueType = "Wrong total shown in bill",
            status = SupportTicketStatus.OPEN,
            createdAt = fakeDate.minusDays(1)
        )
    )

    val fakeResolvedTickets = emptyList<SupportTicketsModel>()
    TicketsScreen(
        open = fakeOpenTickets,
        resolved = fakeResolvedTickets,
        onCardClick = {}
    )
}
