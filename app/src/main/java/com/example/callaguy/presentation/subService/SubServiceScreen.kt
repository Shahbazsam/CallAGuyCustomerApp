package com.example.callaguy.presentation.subService

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.callaguy.R
import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import com.example.callaguy.presentation.loadingScreens.ErrorScreen
import com.example.callaguy.presentation.loadingScreens.SubServicesLoadingScreen

@Composable
fun SubServiceScreenRoot(
    onCardClick: (Int, String , String) -> Unit,
    serviceId: Int,
    serviceName: String,
) {
    val viewModel: SubServiceViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current


    LaunchedEffect(serviceId) {
        viewModel.getSubServices(serviceId)
    }

    when (state) {
        is SubServiceUiState.Error -> {
            ErrorScreen(
                onRetry = { viewModel.getSubServices(serviceId) }
            )
            val error = (state as SubServiceUiState.Error)
            Toast.makeText(
                context,
                "${error.code} : ${error.message}",
                Toast.LENGTH_LONG
            ).show()
        }

        SubServiceUiState.Idle -> Unit
        SubServiceUiState.Loading -> {
            SubServicesLoadingScreen(
                service = serviceName
            )
        }

        is SubServiceUiState.Success -> {
            SubServiceScreen(
                onCardClick = onCardClick,
                service = serviceName,
                subServices = (state as SubServiceUiState.Success).subServices
            )
        }
    }

}

@Composable
fun SubServiceScreen(
    onCardClick: (Int, String, String) -> Unit,
    service: String,
    subServices: List<SubServiceResponseDto>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAFC))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(Color(0xFFF7FAFC)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sub-Services",
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                fontSize = 22.sp

            )
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = service,
            style = MaterialTheme.typography.displayMedium,
            fontSize = 26.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(subServices) { subService ->
                SubServiceCards(onCardClick, subService)
            }
        }
    }
}




@Composable
fun SubServiceCards(
    onCardClick: (Int, String , String) -> Unit,
    subService: SubServiceResponseDto,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FAFC))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = subService.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp,
                    color = Color(0xFF1A1A1A),
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Transform your home with our ${subService.name.lowercase()} service.",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 14.sp,
                    color = Color(0xFF57738F)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "₽${subService.basePrice}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF57738F)
                )

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(
                    onClick = {
                        onCardClick(subService.id, subService.name , subService.imageUrl)
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color(0xFFE8EDF5),
                        contentColor = Color(0xFF777777)
                    )
                ) {
                    Text(
                        text = "Book Now",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                    )
                }
            }

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(subService.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
    }
}


@Composable
fun PriceTag(tag: String, price: String, modifier: Modifier = Modifier) {
    Surface(
        color = Color(0x112196F3),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(2.dp)
            .wrapContentSize()
    ) {
        Text(
            text = "$tag $price",
            color = Color(0xFF777777),
            fontSize = 19.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}


/*@Preview
@Composable
fun Preview() {
    SubServiceScreen(
        service = "Cleaning",
        subServices = listOf(
            SubServiceResponseDto(
                id = 1,
                name = "cleaning",
                basePrice = BigDecimal(500.00),
                visitCharge = BigDecimal(200.00),
                imageUrl = null
            ),
            SubServiceResponseDto(
                id = 1,
                name = "cleaning",
                basePrice = BigDecimal(500.00),
                visitCharge = BigDecimal(200.00),
                imageUrl = null
            ),
            SubServiceResponseDto(
                id = 1,
                name = "cleaning",
                basePrice = BigDecimal(500.00),
                visitCharge = BigDecimal(200.00),
                imageUrl = null
            ),
            SubServiceResponseDto(
                id = 1,
                name = "cleaning",
                basePrice = BigDecimal(500.00),
                visitCharge = BigDecimal(200.00),
                imageUrl = null
            ),
            SubServiceResponseDto(
                id = 1,
                name = "cleaning",
                basePrice = BigDecimal(500.00),
                visitCharge = BigDecimal(200.00),
                imageUrl = null
            )
        )
    )
}*/

