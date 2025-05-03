package com.example.callaguy.presentation.Service

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy.R
import com.example.callaguy.presentation.BottomNavigationBar

@Composable
fun ServiceScreen(modifier: Modifier = Modifier) {

}


@Composable
fun Services(modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = {
            BottomNavigationBar(
                onHomeClick = {},
                onProfileClick = {}
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFF5F5F5))
                .fillMaxSize()
        ) {
            // Logo Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .offset(y = (-30).dp), // This moves it visually up without pushing content down
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(140.dp),
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            // List of services
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 70.dp) // Adjust this to leave space for the logo
            ) {
                items(fakeData) {
                    ServicesCard(
                        data = it,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ServicesCard(data: Data, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    modifier = Modifier
                        .weight(1f),
                    painter = painterResource(data.imageUrl),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = data.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 20.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = data.description,
                        maxLines = 2,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 14.sp,
                        color = Color(0xFF777777),
                        fontWeight = FontWeight.Normal
                    )
                }
            }


            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .background(
                        color = Color(0x112196F3),
                        shape = RoundedCornerShape(30)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Explore",
                    color = Color(0xFF1976D2),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ServicePreview() {
    Services()
}
@Preview(showBackground = true)
@Composable
fun ServiceCardPreview() {
    ServicesCard(fakeData[1])
}

data class Data(
    val id : Int ,
    val name : String,
    val description : String,
    val imageUrl : Int,

    )

val fakeData = listOf(
    Data(
        id = 1,
        name = "Cleaning",
        description = "Professional home and office cleaning services.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 2,
        name = "Plumbing",
        description = "Leak repair, installation, and plumbing maintenance.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 3,
        name = "Electrical",
        description = "Fix wiring issues, install appliances and more.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 4,
        name = "Pest Control",
        description = "Safe and effective pest removal services.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 5,
        name = "Painting",
        description = "Interior and exterior painting for your home or office.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 6,
        name = "AC Services",
        description = "AC installation, repair, and maintenance.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 7,
        name = "Carpentry",
        description = "Furniture repair, custom woodwork, and more.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 8,
        name = "Gardening",
        description = "Lawn care, landscaping, and garden maintenance.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 9,
        name = "Home Appliance Repair",
        description = "Fix refrigerators, washing machines, and more.",
        imageUrl = R.drawable.logo
    ),
    Data(
        id = 10,
        name = "Sanitization",
        description = "Home and office deep sanitization services.",
        imageUrl = R.drawable.logo
    )
)
