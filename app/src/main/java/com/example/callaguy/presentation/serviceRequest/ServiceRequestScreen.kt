package com.example.callaguy.presentation.serviceRequest

import android.app.TimePickerDialog
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.callaguy.R
import com.example.callaguy.presentation.auth.MyTextField
import com.example.callaguy.presentation.loadingScreens.ErrorScreen
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun ServiceRequestScreen(
    onGoToOrders: () -> Unit,
    subServiceId: Int,
    subServiceName: String,
    @DrawableRes subServiceImage: Int,
) {
    val viewmodel: ServiceRequestViewModel = hiltViewModel()
    val formState = viewmodel.requestFormState
    val state by viewmodel.uiState.collectAsStateWithLifecycle()
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
    when (state) {
        is ServiceRequestUiState.Error -> {
            ErrorScreen(
                onRetry = {
                    viewmodel.onEvent(ServiceRequestFormEvent.Submit(subServiceId))
                }
            )
        }
        ServiceRequestUiState.Idle -> {
            Column(
                modifier = Modifier
                    .background(Color(0xFFF5F5F5))
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                    text = "Book a service",
                    fontSize = 22.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D141C),
                    style = MaterialTheme.typography.titleLarge,
                )
                ServiceInfo(subServiceName, subServiceImage)
                Spacer(Modifier.height(12.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    item {
                        DateTimePickerSection(
                            date = selectedDate,
                            time = selectedTime,
                            onDateChange = { selectedDate = it },
                            onTimeChange = { selectedTime = it }
                        )
                    }

                    item {
                        MyTextField(
                            modifier = Modifier
                                .size(width = 358.dp, height = 104.dp),
                            stateError = null,
                            value = formState.address,
                            onValueChange = {
                                viewmodel.onEvent(ServiceRequestFormEvent.AddressChanged(it))
                            },
                            label = "Address",
                        )

                        MyTextField(
                            modifier = Modifier
                                .size(width = 358.dp, height = 144.dp),
                            stateError = null,
                            value = formState.specialInstructions,
                            onValueChange = {
                                viewmodel.onEvent(ServiceRequestFormEvent.InstructionChanged(it))
                            },
                            label = "Special Instruction",
                        )
                        Log.d("Date ", "$selectedDate")
                        Log.d("time ", "$selectedTime")
                    }


                    item {
                        Spacer(Modifier.height(200.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                                .size(width = 55.dp, height = 55.dp),
                            onClick = {
                                formState.preferredDate = selectedDate
                                formState.preferredTime = selectedTime
                                viewmodel.onEvent(ServiceRequestFormEvent.Submit(subServiceId))
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(Color(0xFF4A90E2))
                        ) {
                            Text(
                                text = " Confirm & Pay ",
                                color = Color(0xFFFFFFFF)
                            )
                        }
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
        ServiceRequestUiState.Loading -> {
            ServiceRequestLoadingScreen()
        }
        is ServiceRequestUiState.Success -> {
            BookingConfirmationScreen(onGoToOrders)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerSection(
    date: LocalDate?,
    time: LocalTime?,
    onDateChange: (LocalDate) -> Unit,
    onTimeChange: (LocalTime) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    Column {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val up = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (up != null) showDatePicker = true
                    }
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFFFFFFF),
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedTextColor = Color(0xFF858585),
                unfocusedTextColor = Color(0xFF858585),
                errorContainerColor = Color(0xFFFFFFFF),
                errorTextColor = Color(0xFF858585)
            ),
            shape = shapes.large,
            value = date?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = "Preferred Date",
                    color = Color(0xFFBBBBBB),
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Select date",
                    tint = Color(0xFFBBBBBB)
                )
            }
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val up = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (up != null) showTimePicker = true
                    }
                },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFFFFFFF),
                unfocusedContainerColor = Color(0xFFFFFFFF),
                focusedBorderColor = Color(0xFFE0E0E0),
                unfocusedBorderColor = Color(0xFFE0E0E0),
                focusedTextColor = Color(0xFF858585),
                unfocusedTextColor = Color(0xFF858585),
                errorContainerColor = Color(0xFFFFFFFF),
                errorTextColor = Color(0xFF858585)
            ),
            shape = shapes.large,
            value = time?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "",
            onValueChange = {},
            readOnly = true,
            label = {
                Text(
                    text = "Preferred Time",
                    color = Color(0xFFBBBBBB),
                    fontWeight = FontWeight.SemiBold
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Select time",
                    tint = Color(0xFFBBBBBB)
                )
            }
        )
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateChange(localDate)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        val context = LocalContext.current
        val now = LocalTime.now()
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                onTimeChange(LocalTime.of(hour, minute))
                showTimePicker = false
            },
            now.hour,
            now.minute,
            false
        ).show()
    }
}


@Composable
fun ServiceInfo(
    subServiceName: String,
    @DrawableRes subServiceImage: Int,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Surface(
            color = Color(0x112196F3),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .size(width = 64.dp, height = 64.dp)
                .padding(2.dp)
                .wrapContentSize()
        ) {
            Image(
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .size(width = 24.dp, height = 24.dp),
                painter = painterResource(subServiceImage),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
        Text(
            modifier = Modifier
                .padding(18.dp),
            text = subServiceName,
            fontSize = 18.sp,
            fontStyle = FontStyle.Normal,
            color = Color(0xFF0D141C),
        )

    }
}


@Composable
fun ServiceRequestLoadingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color(0xFF4A90E2),
            strokeWidth = 6.dp
        )
    }
}

@Composable
fun BookingConfirmationScreen(
    onGoToOrders: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp), // to leave space for button
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circular Image with Tick
            Image(
                painter = painterResource(id = R.drawable.tick_circle), // Your tick icon
                contentDescription = "Success Tick",
                modifier = Modifier
                    .size(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Booking Confirmed",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your booking has been successfully confirmed.\nYou will receive a confirmation email shortly.",
                fontSize = 16.sp,
                color = Color(0xFF7F8C8D),
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = onGoToOrders,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(70.dp)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2))
        ) {
            Text(
                text = "Go to Orders",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun preview2(modifier: Modifier = Modifier) {
    BookingConfirmationScreen(
        onGoToOrders = {}
    )
}
