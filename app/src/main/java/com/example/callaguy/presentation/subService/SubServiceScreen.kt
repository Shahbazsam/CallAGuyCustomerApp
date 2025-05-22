package com.example.callaguy.presentation.subService

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy.R
import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import com.example.callaguy.presentation.loadingScreens.ErrorScreen
import com.example.callaguy.presentation.loadingScreens.SubServicesLoadingScreen
import java.math.BigDecimal

@Composable
fun SubServiceScreenRoot(
    serviceId : Int,
    serviceName : String ,
    modifier: Modifier = Modifier
) {
    val viewModel : SubServiceViewModel = hiltViewModel()
    viewModel.getSubServices(serviceId)
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when(state){
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
                service = serviceName,
                subServices = (state as SubServiceUiState.Success).subServices
            )
        }
    }

}


@Composable
fun SubServiceScreen(
    service: String,
    subServices: List<SubServiceResponseDto>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(Color(0xFFF5F5F5))
            .fillMaxWidth()
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(80.dp),
            painter = painterResource(R.drawable.logoo),
            contentDescription = null,
            tint = Color(0xFF777777)
        )
        Text(
            modifier = Modifier
                .padding( start = 8.dp),
            text = "$service Services :",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 26.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        LazyColumn {
            items(subServices) { subService ->
                SubServiceCards(subService)
            }
            item {
               Spacer(Modifier.height(12.dp))
            }
        }
    }
}


@Composable
fun SubServiceCards(subService: SubServiceResponseDto, modifier: Modifier = Modifier) {
    Card(
        modifier = Modifier
            .padding(6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF)
        )
    ) {
        Column {
            Image(
                painter = painterResource(R.drawable.cleaning),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp , bottom = 10.dp),
                    text = subService.name,
                    fontSize = 22.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF777777),
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(32.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFF4787D3)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 4.dp, end = 8.dp),
                    text = "4.4 ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4787D3)
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 20.dp)
            ) {
                PriceTag(
                    modifier = Modifier
                        .weight(1f),
                    tag = "Base price :",
                    price = "₽${subService.basePrice}",
                )
                PriceTag(
                    modifier = Modifier
                        .weight(1f),
                    tag = "Visit charge :",
                    price = "₽${subService.visitCharge}",
                )
            }
            TextButton(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.End),
                onClick = {},
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color(0x112196F3)
                )
            ) {
                Text(
                    text = "Book Now",
                    color = Color(0xFF1976D2),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color(0xFF1976D2)
                )
            }

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


@Preview
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
}

