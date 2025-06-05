package com.example.callaguy.presentation.auth

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.callaguy.R
import com.example.callaguy.domain.model.ResultClass


@Composable
fun RegistrationScreen(
    onNavigateToLogin : () -> Unit
) {
    val viewModel : RegisterViewModel = hiltViewModel()
    val state = viewModel.state

    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.registrationEventChannel.collect{event ->
            when(event) {
                is ResultClass.Unauthorized -> {
                    Toast.makeText(
                        context,
                        "Unauthorized",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ResultClass.Authorized<*> -> {
                    Toast.makeText(
                        context,
                        "Registration Successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is ResultClass.UnKnownError<*> -> {
                    Toast.makeText(
                        context,
                        "Unknown error happened",
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
        Text(
            modifier = Modifier
                .padding(start = 100.dp),
            text = " Create Account ",
            style = MaterialTheme.typography.headlineMedium,
            fontSize = 28.sp,
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            item {
                MyTextField(
                    value = state.userName,
                    onValueChange = {
                        viewModel.onEvent(RegistrationFormEvent.UserNameChanged(it))
                    },
                    label = "UserName",
                    isPassword = false,
                    stateError = null
                )
                MyTextField(
                    stateError = state.emailError,
                    value = state.email,
                    onValueChange = {
                        viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
                    },
                    label = "E-mail",
                    isPassword = false,
                )
                MyTextField(
                    stateError = state.passwordError,
                    value = state.password,
                    onValueChange = {
                        viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
                    },
                    label = "Password",
                    isPassword = true,

                )
                MyTextField(
                    value = state.repeatedPassword,
                    onValueChange = {
                        viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
                    },
                    label = "RepeatPassword",
                    isPassword = true,
                    stateError = state.repeatedPasswordError
                )
                MyTextField(
                    value = state.phoneNumber,
                    onValueChange = {
                        viewModel.onEvent(RegistrationFormEvent.PhoneNumberChanged(it))
                    },
                    label = "Phone",
                    isPassword = false,
                    stateError = null
                )
                MyTextField(
                    value = state.address,
                    onValueChange = {
                        viewModel.onEvent(RegistrationFormEvent.AddressChanged(it))
                    },
                    label = "Address",
                    isPassword = false,
                    stateError = null
                )
            }
            item {
                Spacer(Modifier.height(32.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .size(width = 55.dp , height = 55.dp),
                    onClick = {
                        viewModel.onEvent(RegistrationFormEvent.Submit)
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0xFF4A90E2))
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillParentMaxSize()
                    ) {
                        Text(
                            text = " Register ",
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
            }
        }
        Row(
            Modifier.padding(bottom = 46.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp , start = 48.dp),
                text = " Already have a account ? ",
                style = MaterialTheme.typography.displayMedium,
                fontSize = 18.sp,
                color = Color(0xFF333333),
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .clickable {
                        onNavigateToLogin()
                    }
                    .padding(top = 4.dp , start = 2.dp),
                text = "Log In ",
                style = MaterialTheme.typography.displayMedium,
                fontSize = 18.sp,
                color = Color(0xFF4A90E2),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    stateError : String ? ,
    value : String ,
    onValueChange : (String) -> Unit ,
    label : String ,
    isPassword : Boolean = false,
) {
    Column {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 12.dp , end = 12.dp , top = 12.dp),
            shape = shapes.large,
            value = value,
            isError = stateError != null,
            onValueChange = onValueChange,
            colors =  OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFFFFFFF),
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedTextColor = Color(0xFF858585),
                unfocusedTextColor = Color(0xFF858585),
                errorContainerColor = Color(0xFFFFFFFF),
                errorTextColor = Color(0xFF858585)
            ) ,
            label = {Text(
                text =  label,
                color = Color(0xFFBBBBBB),
                fontWeight = FontWeight.SemiBold
            )},
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if(isPassword) KeyboardType.Password else KeyboardType.Text
            )
        )
        if (stateError != null) {
            Text(
                modifier = Modifier
                    .padding(start =28.dp , top = 8.dp),
                text = stateError,
                color = Color.Red,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

/*

@Preview(showBackground = true)
@Composable
fun Preview () {
    RegistrationScreen()
}*/
