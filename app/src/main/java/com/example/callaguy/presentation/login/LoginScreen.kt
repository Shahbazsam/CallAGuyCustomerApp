package com.example.callaguy.presentation.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.callaguy.R
import com.example.callaguy.domain.model.ResultClass
import com.example.callaguy.presentation.auth.MyTextField
import kotlinx.coroutines.delay


@Composable
fun LoginScreen (
    onNavigateToHome : () -> Unit,
    onNavigateToRegister : () -> Unit
    ) {

    val viewModel : LoginViewModel = hiltViewModel()
    val state = viewModel.state

    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.loginChannel.collect{ event ->
            when(event) {
                is ResultClass.Authorized<*> -> {
                    Toast.makeText(
                        context,
                        "Login Successful",
                        Toast.LENGTH_LONG
                    ).show()
                    delay(500)
                    onNavigateToHome()
                }
                is ResultClass.UnKnownError<*> -> {
                    Toast.makeText(
                        context,
                        " Unknown Error happened",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ResultClass.Unauthorized<*> -> {
                    Toast.makeText(
                        context,
                        " Unauthorized ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }
    Column(
        modifier = Modifier
            .background(Color(0xFFF5F5F5))
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(140.dp),
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Spacer(Modifier.height(30.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = " Welcome back \uD83D\uDC4B ",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 28.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(30.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = " Login In To Continue ",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 18.sp,
            color = Color(0xFF777777),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        MyTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(LoginFormEvent.EmailChanged(it))
            },
            label = "E-mail",
            isPassword = false,
            stateError = state.emailError
        )
        MyTextField(
            stateError = state.passwordError,
            value = state.password,
            onValueChange = {
                viewModel.onEvent(LoginFormEvent.PasswordChanged(it))
            },
            label = "Password",
            isPassword = false,
        )
        Spacer(Modifier.height(30.dp))
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = " Forgot Password ?  ",
            style = MaterialTheme.typography.displayMedium,
            fontSize = 18.sp,
            color = Color(0xFF4A90E2),
            fontWeight = FontWeight.Normal
        )
        Spacer(Modifier.weight(1f))
        Column (
            verticalArrangement = Arrangement.Bottom

        ){
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .size(width = 55.dp , height = 55.dp),
                onClick = {
                    viewModel.onEvent(LoginFormEvent.Submit)
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(Color(0xFF4A90E2))
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = " Sign In ",
                        color = Color(0xFFFFFFFF)
                    )
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = Color(0xFFFFFFFF),
                            strokeWidth = 2.dp,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 12.dp)
                                .size(20.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(50.dp))
            Row(
                modifier =Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 60.dp)
            ){
                Text(
                    modifier = Modifier
                        .padding(top = 4.dp ),
                    text = " Don't have a account ? ",
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 18.sp,
                    color = Color(0xFF777777),
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier
                        .clickable { onNavigateToRegister() }
                        .padding(top = 4.dp ),
                    text = " Register ",
                    style = MaterialTheme.typography.displayMedium,
                    fontSize = 18.sp,
                    color = Color(0xFF4A90E2),
                    fontWeight = FontWeight.Normal
                )
            }

        }
    }
}


/*
@Preview(showBackground = true)
@Composable
fun LoginPreview(modifier: Modifier = Modifier) {
    LoginScreen()
}*/
