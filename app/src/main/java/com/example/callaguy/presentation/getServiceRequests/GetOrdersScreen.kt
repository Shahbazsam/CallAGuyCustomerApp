package com.example.callaguy.presentation.getServiceRequests

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.callaguy.R
import com.example.callaguy.domain.model.GetServiceRequestModel
import com.example.callaguy.domain.model.ServiceRequestStatusModel
import com.example.callaguy.presentation.loadingScreens.ErrorScreen
import com.example.callaguy.presentation.serviceRequest.ServiceRequestLoadingScreen
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun GetOrderRoot(
    modifier: Modifier = Modifier,
    onCardClick: (GetServiceRequestModel) -> Unit
) {
    val viewmodel: GetServiceRequestsViewModel = hiltViewModel()
    val state by viewmodel.uiState.collectAsStateWithLifecycle()

    when (state) {
        is GetServiceRequestsUiState.Error -> {
            ErrorScreen(
                onRetry = {
                    viewmodel.fetchOrders()
                }
            )
        }
        GetServiceRequestsUiState.Idle -> Unit
        GetServiceRequestsUiState.Loading -> {
            ServiceRequestLoadingScreen()
        }

        is GetServiceRequestsUiState.Success -> {
            GetOrderScreen(
                upcoming = (state as GetServiceRequestsUiState.Success).onGoing,
                past = (state as GetServiceRequestsUiState.Success).past,
                onCardClick = onCardClick
            )
        }
    }

}

@Composable
fun GetOrderScreen(
    upcoming: List<GetServiceRequestModel>,
    past: List<GetServiceRequestModel>,
    onCardClick: (GetServiceRequestModel) -> Unit
) {

    var upcomingOrPast by remember { mutableStateOf(false) }
    val currentOrders = if (!upcomingOrPast) upcoming else past

    Column(
        modifier = Modifier
            .background(Color(0xFFF7FAFC))
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        OrderOrTicketToggleButtons (
            leftText = stringResource(R.string.upcoming),
            rightText = stringResource(R.string.past),
            upcomingOrPast = upcomingOrPast
        ) {
            upcomingOrPast = it
        }
        Spacer(Modifier.height(8.dp))

        if (currentOrders.isNotEmpty()) {
            OrderList(
                orders = currentOrders,
                onCardClick = onCardClick
            )
        } else {
            NoOrderYet()
        }
    }
}

@Composable
fun OrderList(
    orders: List<GetServiceRequestModel>,
    onCardClick: (GetServiceRequestModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(orders) { order ->
            OrderCard(
                order,
                onCardClick
            )
        }

    }

}

@Composable
fun OrderCard(
    order: GetServiceRequestModel,
    onCardClick: (GetServiceRequestModel) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF7FAFC)
        )
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1.3f)
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp, start = 6.dp, bottom = 4.dp),
                    text = order.status.toString(),
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF4A739C)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp, bottom = 4.dp),
                    text = order.subService,
                    style = MaterialTheme.typography.labelMedium,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )
                Text(
                    modifier = Modifier
                        .padding(start = 6.dp, bottom = 6.dp),
                    text = formatPreferredDateTime(order.preferredDate, order.preferredTime),
                    fontStyle = FontStyle.Normal,
                    fontSize = 15.sp,
                    color = Color(0xFF4A739C)
                )
                Button(
                    modifier = Modifier
                        .padding(6.dp),
                    onClick = {
                        onCardClick(order)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE8EDF5),
                        contentColor = Color(0xFF777777)
                    ),
                    shape = RoundedCornerShape(20.dp),
                    elevation = null
                ) {
                    Text("View Details", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }

            }
            AsyncImage(
                modifier = Modifier
                    .padding(8.dp)
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                model = order.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Composable
fun NoOrderYet() {
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
            painter = painterResource(R.drawable.no_orders),
            contentDescription = "No Orders",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .size(400.dp)
                .padding(bottom = 24.dp)
        )

        Text(
            text = "No Orders Yet",
            fontSize = 32.sp,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4A739C)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "You don’t have any upcoming or past orders.",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color(0xFF777777),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}


@Composable
fun OrderOrTicketToggleButtons(
    leftText : String,
    rightText : String,
    upcomingOrPast: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFFFFFFF))
    ) {
        val buttonModifier = Modifier
            .weight(1f)
            .padding(2.dp)

        Button(
            onClick = { onToggle(false) },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!upcomingOrPast) Color(0xFFE8EDF5) else Color.Transparent,
                contentColor = Color(0xFF777777)
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = null
        ) {
            Text(leftText)
        }

        Button(
            onClick = { onToggle(true) },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (upcomingOrPast) Color(0xFFE8EDF5) else Color.Transparent,
                contentColor = Color(0xFF777777)
            ),
            shape = RoundedCornerShape(20.dp),
            elevation = null
        ) {
            Text(rightText)
        }
    }
}

fun formatPreferredDateTime(
    date: LocalDate,
    time: LocalTime
): String {
    val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.ENGLISH)
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

    val formattedDate = date.format(dateFormatter)
    val formattedTime = time.format(timeFormatter)

    return "$formattedDate · $formattedTime"
}



