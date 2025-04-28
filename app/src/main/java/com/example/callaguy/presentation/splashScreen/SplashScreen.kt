package com.example.callaguy.presentation.splashScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.callaguy.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onNextScreen : () -> Unit) {

    val size = remember { Animatable(75.dp , Dp.VectorConverter) }

    LaunchedEffect(Unit) {
        launch {
            while (true) {
                size.animateTo(
                    targetValue = 200.dp,
                    animationSpec = tween(durationMillis = 800, easing = LinearEasing)
                )
                size.animateTo(
                    targetValue = 150.dp,
                    animationSpec = tween(durationMillis = 800, easing = LinearEasing)
                )
            }
        }
        delay(3000)
        onNextScreen()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo),
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier.size(size.value)
        )
    }
}

@Preview
@Composable
fun Preview() {
    SplashScreen(
        onNextScreen = {}
    )
}