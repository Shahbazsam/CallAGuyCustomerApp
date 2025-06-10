package com.example.callaguy.presentation.getServiceRequests

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.StickyNote2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy.domain.model.GetServiceRequestModel
import com.example.callaguy.domain.model.ServiceRequestStatusModel
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun GetDetailedScreen(
    onRaise : (Int) -> Unit,
    order: GetServiceRequestModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAFC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp , bottom = 6.dp),
            text = "Booking Details",
            color = Color.DarkGray,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                DetailsField(
                    icon = Icons.Outlined.Home,
                    text = order.subService,
                    textColor = Color.Black,
                    tint = null ,
                    isBold = true
                )
                DetailsField(
                    icon = Icons.Outlined.Badge,
                    text = " Professional ID : ${order.professionalId?.toString() ?: "Not assigned"} ",
                    textColor = Color.Black,
                    tint = null,
                    isBold = false
                )
                DetailsField(
                    icon = Icons.Outlined.DateRange,
                    text = formatPreferredDateTime(order.preferredDate, order.preferredTime),
                    textColor = Color(0xFF4A739C),
                    tint = Color(0xFF4A739C),
                    isBold = false
                )
                DetailsField(
                    icon = Icons.Outlined.Schedule,
                    text = order.createdAt.format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy, hh:mm a")),
                    textColor = Color(0xFF4A739C),
                    tint = Color(0xFF4A739C),
                    isBold = false
                )
                DetailsField(
                    icon = Icons.Outlined.Payments,
                    text = "Amount Left : ${order.amount} ",
                    textColor = Color.Black,
                    tint = null,
                    isBold = false
                )
                DetailsField(
                    icon = Icons.Outlined.LocationOn,
                    text = order.address,
                    textColor = Color.Black,
                    tint = null,
                    isBold = false
                )
                DetailsField(
                    icon = Icons.Outlined.StickyNote2,
                    text = if (order.specialInstructions.isNullOrBlank()) "No Instructions" else order.specialInstructions,
                    textColor = Color.Black,
                    tint = null,
                    isBold = false
                )
                val statusUi = getStatusUI(order.status)
                DetailsField(
                    icon = statusUi.icon,
                    text = "Status : ${order.status}",
                    textColor = statusUi.color,
                    tint = statusUi.color,
                    isBold = true
                )
                Text(
                    modifier = Modifier
                        .padding(14.dp),
                    text = "Have a Problem ? Raise a Ticket",
                    fontSize = 18.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
            Row(
                modifier = Modifier
                    .padding(top = 26.dp)
                    .fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        onRaise(order.id)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0xFF4A90E2))
                ) {
                    Text(
                        text = " Raise Ticket ",
                        color = Color(0xFFFFFFFF)
                    )
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0xFF4A90E2))
                ) {
                    Text(
                        text = " Pay ",
                        color = Color(0xFFFFFFFF)
                    )
                }
            }

    }
}

@Composable
fun DetailsField(
    icon: ImageVector,
    text: String,
    textColor: Color,
    tint: Color?,
    isBold : Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 16.dp, start = 14.dp)
                .size(56.dp)
                .background(Color(0xFFE8EDF5), shape = RoundedCornerShape(8.dp))
        ) {
            Icon(
                modifier = Modifier
                    .size(28.dp),
                imageVector = icon,
                contentDescription = null,
                tint = tint ?: Color.DarkGray

            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            modifier = Modifier
                .padding(start = 8.dp, top = 28.dp),
            color = textColor,
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontSize = 18.sp,

            )
    }

}


data class StatusUI(val icon: ImageVector, val color: Color)


fun getStatusUI(status: ServiceRequestStatusModel): StatusUI {
    return when (status) {
        ServiceRequestStatusModel.REQUESTED -> StatusUI(Icons.Outlined.HourglassEmpty, Color.Black)
        ServiceRequestStatusModel.ACCEPTED -> StatusUI(Icons.Outlined.CheckCircle, Color(0xFF4A90E2)) // Blue
        ServiceRequestStatusModel.COMPLETED -> StatusUI(Icons.Outlined.DoneAll, Color(0xFF2ECC71)) // Green
        ServiceRequestStatusModel.CANCELLED -> StatusUI(Icons.Outlined.Cancel, Color(0xFFE74C3C)) // Red
    }
}
