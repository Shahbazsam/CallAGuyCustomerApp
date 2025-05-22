package com.example.callaguy.presentation.loadingScreens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy.R

@Composable
fun SubServicesLoadingScreen(service: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Icon(
            modifier = Modifier
                .align(  Alignment.CenterHorizontally)
                .size(80.dp),
            painter = painterResource(R.drawable.logoo),
            contentDescription = null,
            tint = Color(0xFF777777)
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "$service Services :",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 26.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(5) { // Number of shimmer cards to show
                Card(
                    modifier = Modifier
                        .padding(6.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column {
                        // Image shimmer
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .shimmerEffect()
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, top = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .width(120.dp)
                                    .height(28.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .shimmerEffect()
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Spacer(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(50))
                                    .shimmerEffect()
                            )
                            Spacer(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp)
                                    .width(30.dp)
                                    .height(24.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .shimmerEffect()
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 20.dp)
                                .fillMaxWidth()
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(30.dp)
                                    .padding(horizontal = 10.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerEffect()
                            )
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(30.dp)
                                    .padding(horizontal = 10.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .shimmerEffect()
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .align(Alignment.End)
                                .padding(8.dp)
                                .width(100.dp)
                                .height(36.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}
