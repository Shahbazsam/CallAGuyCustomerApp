package com.example.callaguy.presentation.Service

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.callaguy.R
import com.example.callaguy.data.dto.service.response.ServiceResponseDto
import com.example.callaguy.presentation.BottomNavBar
import com.example.callaguy.presentation.loadingScreens.ErrorScreen
import com.example.callaguy.presentation.loadingScreens.ServicesLoadingScreen

@Composable
fun ServiceScreen(
    onHomeClick : () -> Unit,
    onProfileClick : () -> Unit,
    onCardClick: (Int) -> Unit
) {
    val viewmodel : ServiceViewModel = hiltViewModel()
    val state by viewmodel.uiState.collectAsStateWithLifecycle()

    when(state) {
        is ServiceUiState.Error -> {
            ErrorScreen (
                onRetry = viewmodel::fetchServices
            )
        }
        ServiceUiState.Idle -> Unit
        ServiceUiState.Loading -> {
            ServicesLoadingScreen()
        }
        is ServiceUiState.success -> {
            Services(
                services = (state as ServiceUiState.success).services,
                onProfileClick = onProfileClick,
                onHomeClick = onHomeClick,
                onCardClick = onCardClick
            )
        }
    }
}

@Composable
fun Services(
    services : List<ServiceResponseDto>,
    onCardClick: (Int) -> Unit,
    onHomeClick : () -> Unit,
    onProfileClick : () -> Unit
) {
    Box(
        modifier = Modifier
            .systemBarsPadding()
            .background(Color(0xFFF5F5F5))
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .offset(y = (-35).dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier.size(140.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp)
        ) {
            /*item {
                LazyRow(
                    modifier = Modifier
                        .padding(top = 20.dp)
                ) {
                    items(services) {
                        ServicesCard(
                            onCardClick = onCardClick,
                            service = it,
                            modifier = Modifier
                                .padding(12.dp)
                                .height(180.dp)
                                .width(300.dp)
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
            }*/
            items(services) {
                ServicesCard(
                    onCardClick = onCardClick,
                    service = it,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }
            item { Spacer(Modifier.height(77.dp)) }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            BottomNavBar(
                onProfileClick = {onProfileClick()},
                onHomeClick = {onHomeClick()}
            )
        }
    }
}


@Composable
fun ServicesCard( onCardClick: (Int) -> Unit , service: ServiceResponseDto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .clickable {
                onCardClick(
                    service.id
                )
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp) // Ensure height to allow bottom alignment
                .padding(16.dp)
        ){
            Row(
                modifier = Modifier
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(service.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .weight(1f),
                        /* error = painterResource(R.drawable.),
                         placeholder = painterResource(R.drawable.),*/
                    )
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 12.dp , bottom = 12.dp)
                ) {
                    Text(
                        text = service.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 20.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = service.description,
                        maxLines = 2,
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 14.sp,
                        color = Color(0xFF777777),
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            TextButton(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.BottomEnd),
                onClick = {
                    onCardClick(
                        service.id
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color(0x112196F3)
                )
            ) {
                Text(
                    text = "Explore",
                    color = Color(0xFF1976D2),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Icon(Icons.Default.ArrowForward, contentDescription = null)
            }
        }
    }
}

