package com.example.callaguy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.callaguy.presentation.auth.RegistrationScreen
import com.example.callaguy.presentation.splashScreen.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavigationManager(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashScreenRoute
    ) {
        composable<SplashScreenRoute> {
            SplashScreen {
                navController.navigate(RegisterScreenRoute)
            }
        }
        composable<RegisterScreenRoute> {
            RegistrationScreen()
        }
    }
}


@Serializable
object SplashScreenRoute

@Serializable
object RegisterScreenRoute