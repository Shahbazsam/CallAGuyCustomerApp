package com.example.callaguy.presentation.Service

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.callaguy.data.dto.service.response.ServiceResponseDto
import com.example.callaguy.presentation.Service.data.featuredOffers
import com.example.callaguy.presentation.Service.data.tipsForYou
import com.example.callaguy.presentation.Service.model.FeaturedOfferItem
import com.example.callaguy.presentation.Service.model.TipItem
import com.example.callaguy.presentation.loadingScreens.ErrorScreen
import com.example.callaguy.presentation.loadingScreens.ServicesLoadingScreen


@Composable
fun ServiceScreenRoot(onCardClick: (Int, String) -> Unit) {
    val viewmodel: ServiceViewModel = hiltViewModel()
    val state by viewmodel.uiState.collectAsStateWithLifecycle()
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAFC)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is ServiceUiState.Error -> {
                ErrorScreen(
                    onRetry = viewmodel::fetchServices
                )
            }
            ServiceUiState.Idle -> Unit
            ServiceUiState.Loading -> {
                ServicesLoadingScreen()
            }
            is ServiceUiState.success -> {
                ServiceScreen(
                    services = (state as ServiceUiState.success).services,
                    onCardClick = onCardClick
                )
            }
        }

    }
}

@Composable
fun ServiceScreen(
    services: List<ServiceResponseDto>,
    onCardClick: (Int, String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .background(Color(0xFFF7FAFC)),
    ) {
        item {
            TopBar()
        }
        item {
            SectionHeader(title = "Tips For You")
            Spacer(modifier = Modifier.height(12.dp))
            TipsSection(tipsForYou)
        }

        item {
            SectionHeader(title = "Services :")
            Spacer(modifier = Modifier.height(12.dp))
            ServicesSection(
                services = services,
                onCardClick = onCardClick
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            SectionHeader(title = "Featured")
            Spacer(modifier = Modifier.height(12.dp))
            FeaturedSection(featuredOffers)

        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = "Home",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
            modifier = Modifier
                .padding(end = 12.dp)
                .weight(1f)
        )
        Button(
            onClick = {},
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color(0xFFE8EDF5),
                contentColor = Color(0xFF777777)
            ),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .padding(end = 12.dp)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications"
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.SemiBold,
        color = Color.DarkGray,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(horizontal = 22.dp, vertical = 8.dp)
    )
}

@Composable
fun TipsSection(tips: List<TipItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tips) { tip ->
            Card(
                modifier = Modifier
                    .width(240.dp)
                    .height(220.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAFC))
            ) {
                Column {
                    Image(
                        painter = painterResource(tip.imageUrl),
                        contentDescription = tip.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 240.dp, height = 135.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier
                            .padding(start = 6.dp, top = 4.dp, bottom = 4.dp),
                        text = tip.title,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 6.dp, top = 4.dp, bottom = 4.dp),
                        text = tip.subtitle,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        color = Color(0xFF57738F)
                    )
                }
            }
        }
    }
}

@Composable
fun ServicesSection(
    services: List<ServiceResponseDto>,
    onCardClick: (Int, String) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        services.chunked(2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                rowItems.forEach { service ->
                    Card(
                        modifier = Modifier
                            .clickable {
                                onCardClick(
                                    service.id,
                                    service.name
                                )
                            }
                            .weight(1f)
                            .size(width = 173.dp, height = 262.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAFC))
                    ) {
                        Column {
                            AsyncImage(
                                model = service.imageUrl,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .size(173.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 4.dp, bottom = 4.dp),
                                text = service.name,
                                fontStyle = FontStyle.Normal,
                                fontSize = 14.sp,
                                color = Color.DarkGray
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 4.dp, bottom = 4.dp),
                                text = service.description,
                                maxLines = 2,
                                fontStyle = FontStyle.Normal,
                                fontSize = 14.sp,
                                color = Color(0xFF57738F)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FeaturedSection(featured: List<FeaturedOfferItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(featured) { item ->
            Card(
                modifier = Modifier
                    .width(240.dp)
                    .height(220.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAFC))
            ) {
                Column {
                    Image(
                        painter = painterResource(item.imageUrl),
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(width = 240.dp, height = 135.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp, top = 4.dp, bottom = 4.dp),
                        text = item.title,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 12.dp, top = 4.dp, bottom = 4.dp),
                        text = item.subtitle,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        color = Color(0xFF57738F)
                    )
                }
            }
        }
    }
}




