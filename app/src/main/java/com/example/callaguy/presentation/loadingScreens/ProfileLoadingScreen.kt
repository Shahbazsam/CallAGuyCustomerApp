package com.example.callaguy.presentation.loadingScreens

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.callaguy.R
import com.example.callaguy.presentation.BottomNavBar
import com.example.callaguy.presentation.profile.ProfileFieldCard
import com.example.callaguy.presentation.profile.UpdateProfileImage

@Composable
fun ProfileLoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.3f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .shimmerEffect(),
                painter = painterResource(R.drawable.logo),
                contentDescription = "App Logo",
                contentScale = ContentScale.Crop,
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .aspectRatio(1f)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo), // Replace with real image if available
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF4A90E2), CircleShape)
                        .shimmerEffect(),
                )
                TextButton(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .padding(end = 5.dp, bottom = 5.dp)
                        .size(50.dp)
                        .align(Alignment.BottomEnd)
                        .shimmerEffect(),
                    onClick = {},
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .weight(1.9f)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileFieldCard(label = "Name", value = "", modifier = Modifier.shimmerEffect())
            ProfileFieldCard(label = "Email", value = "", modifier = Modifier.shimmerEffect())
            ProfileFieldCard(label = "Phone", value = "", modifier = Modifier.shimmerEffect())
            ProfileFieldCard(label = "Address", value = "", modifier = Modifier.shimmerEffect())

        }
        Column(
            modifier = Modifier
                .weight(0.6f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log Out ",
                color = Color(0xFF4A90E2),
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .shimmerEffect()
            )
        }
    }
}