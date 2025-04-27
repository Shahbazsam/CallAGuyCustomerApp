package com.example.callaguy.presentation.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.callaguy.R


@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .background(Color(0xFFF5F5F5))
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(120.dp),
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
            color = Color(0xFF333333),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(18.dp))
        LazyColumn {
            item {
                MyTextField(
                    value = "",
                    onValueChange = {},
                    label = "UserName",
                    isPassword = false,
                    stateError = null
                )
                MyTextField(
                    value = "",
                    onValueChange = {},
                    label = "E-mail",
                    isPassword = false,
                    stateError = null
                )
                MyTextField(
                    value = "",
                    onValueChange = {},
                    label = "Password",
                    isPassword = false,
                    stateError = null
                )
                MyTextField(
                    value = "",
                    onValueChange = {},
                    label = "RepeatPassword",
                    isPassword = false,
                    stateError = null
                )
                MyTextField(
                    value = "",
                    onValueChange = {},
                    label = "Phone",
                    isPassword = false,
                    stateError = null
                )
                MyTextField(
                    value = "",
                    onValueChange = {},
                    label = "Address",
                    isPassword = false,
                    stateError = null
                )
            }
            item {
                Spacer(Modifier.height(38.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .size(width = 55.dp , height = 55.dp),
                    onClick = {},
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(Color(0xFF4A90E2))
                ) {
                    Text(
                        text = " Register ",
                        color = Color(0xFFFFFFFF)
                    )
                }
            }
        }
        Row {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp , start = 38.dp),
                text = " Already have a account ? ",
                style = MaterialTheme.typography.displayMedium,
                fontSize = 18.sp,
                color = Color(0xFF333333),
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .padding(top = 4.dp , start = 2.dp),
                text = "Login In ",
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
    stateError : String ? ,
    value : String ,
    onValueChange : (String) -> Unit ,
    label : String ,
    isPassword : Boolean = false,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            modifier = Modifier
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
                unfocusedBorderColor = Color(0xFFE0E0E0)
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
                    .padding(start =28.dp , top = 12.dp),
                text = stateError,
                color = Color.Red,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

    }


}


@Preview(showBackground = true)
@Composable
fun Preview () {
    RegistrationScreen()
}