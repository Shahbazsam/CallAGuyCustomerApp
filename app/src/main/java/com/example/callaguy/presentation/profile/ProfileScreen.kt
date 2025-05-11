package com.example.callaguy.presentation.profile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.callaguy.data.local.ProfileEntity
import com.example.callaguy.presentation.BottomNavBar
import com.example.callaguy.presentation.loadingScreens.ErrorScreen


@Composable
fun ProfileScreenRoot(
    onHomeClick : () -> Unit,
    onProfileClick: () -> Unit,
    onLogOut : () -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val context = LocalContext.current

    val profileUIState by viewModel.profileUiState.collectAsStateWithLifecycle()
    val profileNetworkState by viewModel.profileNetworkState.collectAsStateWithLifecycle()
    val profilePictureState by viewModel.profilePictureState.collectAsStateWithLifecycle()

    LaunchedEffect(profileUIState) {
        if (profileUIState == null) {
            viewModel.getProfileInfo()
        }
    }
    when(profileNetworkState) {
        is ProfileNetworkState.Error -> {
            ErrorScreen {
                viewModel.getProfileInfo()
            }
            val error = profileNetworkState as ProfileNetworkState.Error
            Toast.makeText(
                context ,
                "${error.code} : ${error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
        ProfileNetworkState.Idle -> Unit
        ProfileNetworkState.Loading -> TODO() // loading Screen i will add here
        is ProfileNetworkState.success -> Unit
    }
    profileUIState?.let { profile ->
        ProfileScreen(
            profile ,
            profilePictureState ,
            onHomeClick ,
            onProfileClick ,
            onLogOut
        )
    }
}



@Composable
fun ProfileScreen(
    profile: ProfileEntity,
    profilePictureState: UpdateProfileImage,
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onLogOut: () -> Unit
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> /* handle image */ }
    )
    val context = LocalContext.current
    when(profilePictureState) {
        is UpdateProfileImage.Error -> {
            val error = profilePictureState as UpdateProfileImage.Error
            Toast.makeText(
                context ,
                error.message,
                Toast.LENGTH_LONG
            ).show()
        }
        UpdateProfileImage.Idle -> Unit
        UpdateProfileImage.Loading -> Unit
        UpdateProfileImage.Success -> Unit
    }

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
                modifier = Modifier.size(100.dp),
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
                        .border(2.dp, Color(0xFF777777), CircleShape),
                    contentScale = ContentScale.Crop
                )
                if (profilePictureState == UpdateProfileImage.Loading) {
                    CircularProgressIndicator(
                        color = Color.Gray,
                        strokeWidth = 4.dp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(80.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Main content that fills remaining space
        Column(
            modifier = Modifier
                .weight(1.9f)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileFieldCard(label = "Name", value = profile.userName)
            ProfileFieldCard(label = "Email", value = profile.email)
            profile.phone?.let {
                ProfileFieldCard(label = "Phone", value = it)
            }
            profile.address?.let {
                ProfileFieldCard(label = "Address", value = it)
            }
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
                    .clickable { onLogOut() }
            )

            // Bottom navigation
            BottomNavBar(
                onProfileClick = onProfileClick,
                onHomeClick = onHomeClick
            )
        }
    }
}


@Composable
fun ProfileFieldCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                maxLines = 3,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF333333)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewProfile(modifier: Modifier = Modifier) {
    ProfileScreen(
        profile =  ProfileEntity(
            id = 1,
            userName = "shahbaz",
            email = "shahbaz@mail.com",
            phone = "91 7481901186",
            address = "i live not here gdjhdakhkdsfhvdfsjh hfewygukdshf   fdghdfhgdfs bvchjDFBhvbsdkyugafjkw vbfsiygyrwfdbvsdyuybn  fbfvyias ",
            profilePicture = null ,
            isSynced = false
        ),
        onProfileClick = {},
        onLogOut = {},
        onHomeClick = {},
        profilePictureState = UpdateProfileImage.Idle
    )
}
