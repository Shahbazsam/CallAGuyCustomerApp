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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy.R
import com.example.callaguy.domain.model.GetServiceRequestModel
import com.example.callaguy.domain.model.ServiceRequestStatusModel
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun GetOrderRoot(modifier: Modifier = Modifier) {

}

@Composable
fun GetOrderScreen(upcoming : List<GetServiceRequestModel> , past : List<GetServiceRequestModel>  ) {

    var upcomingOrPast by remember { mutableStateOf(true) }
    val currentOrders = if (!upcomingOrPast) upcoming else past

    Column(
        modifier = Modifier
            .background(Color(0xFFF7FAFC))
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        OrderToggleButtons(
            upcomingOrPast = upcomingOrPast
        ) {
            upcomingOrPast = it
        }
        Spacer(Modifier.height(8.dp))

        if (currentOrders.isNotEmpty()) {
            OrderList(orders = currentOrders)
        } else {
            NoOrderYet()
        }
    }
}

@Composable
fun OrderList(orders: List<GetServiceRequestModel>) {
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(orders) { order ->
            OrderCard(order)
        }

    }
    
}

@Composable
fun OrderCard(order : GetServiceRequestModel) {
    Card(
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
            .height(118.dp),
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
                       .padding(top = 4.dp , start = 6.dp , bottom = 4.dp),
                   text = order.status.toString(),
                   fontStyle = FontStyle.Normal,
                   fontSize = 14.sp,
                   color = Color(0xFF4A739C)
               )
               Text(
                   modifier = Modifier
                       .padding(start = 6.dp,bottom = 4.dp),
                   text = order.subService,
                   style = MaterialTheme.typography.labelMedium,
                   fontSize = 18.sp,
                   fontWeight = FontWeight.SemiBold,
                   color = Color.DarkGray
               )
               Text(
                   modifier = Modifier
                       .padding(start = 6.dp, bottom = 6.dp),
                   text = formatPreferredDateTime(order.preferredDate , order.preferredTime),
                   fontStyle = FontStyle.Normal,
                   fontSize = 15.sp,
                   color = Color(0xFF4A739C)
               )
               Button(
                   modifier = Modifier
                       .padding(6.dp),
                   onClick = {  },
                   colors = ButtonDefaults.buttonColors(
                       containerColor =  Color(0xFFE8EDF5),
                       contentColor = Color(0xFF777777)
                   ),
                   shape = RoundedCornerShape(20.dp),
                   elevation = null
               ) {
                   Text("View Details")
               }

           }
           Image(
               modifier = Modifier
                   .clip(RoundedCornerShape(20.dp))
                   .weight(0.7f),
               painter = painterResource(R.drawable.cleaning),
               contentScale = ContentScale.Crop,
               contentDescription = null
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
            painter = painterResource(R.drawable.no_orders), // Replace with your icon
            contentDescription = "No Orders",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp)
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
fun OrderToggleButtons(
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
            Text("Upcoming")
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
            Text("Past")
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



@Preview(showBackground = true)
@Composable
fun preview(modifier: Modifier = Modifier) {
    GetOrderScreen(
        upcoming = fakeServiceRequests,
        past = emptyList()
    )
}


val fakeServiceRequests = listOf(
    GetServiceRequestModel(
        id = 1,
        customerId = 1001,
        professionalId = 2001,
        amount = BigDecimal("49.99"),
        subService = "AC Repair",
        subServiceId = 301,
        status = ServiceRequestStatusModel.REQUESTED,
        preferredDate = LocalDate.now().plusDays(2),
        preferredTime = LocalTime.of(14, 30),
        address = "123 Main Street, Springfield",
        specialInstructions = "Please call before arriving.",
        createdAt = LocalDateTime.now().minusDays(1)
    ),
    GetServiceRequestModel(
        id = 2,
        customerId = 1002,
        professionalId = 2002,
        amount = BigDecimal("99.99"),
        subService = "Home Cleaning",
        subServiceId = 302,
        status = ServiceRequestStatusModel.ACCEPTED,
        preferredDate = LocalDate.now().plusDays(1),
        preferredTime = LocalTime.of(10, 0),
        address = "456 Elm Street, Shelbyville",
        specialInstructions = null,
        createdAt = LocalDateTime.now().minusDays(2)
    ),
    GetServiceRequestModel(
        id = 3,
        customerId = 1003,
        professionalId = null,
        amount = BigDecimal("74.50"),
        subService = "Plumbing",
        subServiceId = 303,
        status = ServiceRequestStatusModel.REQUESTED,
        preferredDate = LocalDate.now().plusDays(3),
        preferredTime = LocalTime.of(9, 15),
        address = "789 Oak Avenue, Capital City",
        specialInstructions = "Fix leaking sink in the kitchen.",
        createdAt = LocalDateTime.now()
    ),
    GetServiceRequestModel(
        id = 4,
        customerId = 1004,
        professionalId = 2003,
        amount = BigDecimal("129.00"),
        subService = "Electrical",
        subServiceId = 304,
        status = ServiceRequestStatusModel.COMPLETED,
        preferredDate = LocalDate.now().minusDays(3),
        preferredTime = LocalTime.of(13, 45),
        address = "321 Pine Road, Ogdenville",
        specialInstructions = null,
        createdAt = LocalDateTime.now().minusDays(4)
    ),
    GetServiceRequestModel(
        id = 5,
        customerId = 1005,
        professionalId = 2004,
        amount = BigDecimal("59.99"),
        subService = "Pest Control",
        subServiceId = 305,
        status = ServiceRequestStatusModel.CANCELLED,
        preferredDate = LocalDate.now().plusDays(5),
        preferredTime = LocalTime.of(16, 0),
        address = "654 Cedar Blvd, North Haverbrook",
        specialInstructions = "Ant infestation in backyard.",
        createdAt = LocalDateTime.now().minusHours(6)
    )
)
