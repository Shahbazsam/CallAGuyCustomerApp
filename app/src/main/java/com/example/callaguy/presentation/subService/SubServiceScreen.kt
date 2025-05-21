package com.example.callaguy.presentation.subService

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.callaguy.R
import com.example.callaguy.data.dto.subServices.SubServiceResponseDto
import java.math.BigDecimal

@Composable
fun SubServiceScreenRoot(modifier: Modifier = Modifier) {

}


@Composable
fun SubServiceScreen(modifier: Modifier = Modifier) {

}


@Composable
fun SubServiceCards(subService: SubServiceResponseDto, modifier: Modifier = Modifier) {
    Card(
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
                        .padding(10.dp),
                    text = subService.name,
                    fontSize = 28.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(32.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFF1976D2)
                )
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 4.dp, end = 8.dp),
                    text = "4.4 ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }

            Row(
                modifier = Modifier
                    .padding( top = 10.dp , bottom = 20.dp )
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
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}


@Preview
@Composable
fun Preview() {
    SubServiceCards(
        subService = SubServiceResponseDto(
            id = 1,
            name = "cleaning",
            basePrice = BigDecimal(500.00),
            visitCharge = BigDecimal(200.00),
            imageUrl = null
        )
    )
}

