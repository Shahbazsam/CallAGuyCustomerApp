package com.example.callaguy.presentation.serviceRequest

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.LocalTime
import android.app.TimePickerDialog
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import com.example.callaguy.R
import java.time.*
import java.time.format.DateTimeFormatter


@Composable
fun ServiceScreenRoot(modifier: Modifier = Modifier) {

}

@Composable
fun ServiceScreen(
    subServiceName: String,
    @DrawableRes subServiceImage: Int,
) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
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
        }
        Log.d("date" , "$selectedDate")
        Log.d("Time" , "$selectedTime")

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

        // ===== Date Picker Field =====
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val up = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (up != null) showDatePicker = true
                    }
                },
            value = date?.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Preferred Date") },
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "Select date")
            }
        )

        // ===== Time Picker Field =====
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        awaitFirstDown(pass = PointerEventPass.Initial)
                        val up = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                        if (up != null) showTimePicker = true
                    }
                },
            value = time?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text("Preferred Time") },
            trailingIcon = {
                Icon(Icons.Default.DateRange, contentDescription = "Select time")
            }
        )
    }

    // ===== Material 3 Date Picker =====
    if (showDatePicker) {
        val datePickerState =  rememberDatePickerState()
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

    // ===== Time Picker Dialog =====
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







@Preview(showBackground = true)
@Composable
fun preview(modifier: Modifier = Modifier) {
    ServiceScreen(
        subServiceName = "Window Clean",
        subServiceImage = R.drawable.cleaning
    )

}