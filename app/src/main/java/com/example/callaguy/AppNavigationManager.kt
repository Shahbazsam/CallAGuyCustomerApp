package com.example.callaguy

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.callaguy.presentation.Service.ServiceScreen
import com.example.callaguy.presentation.auth.RegistrationScreen
import com.example.callaguy.presentation.login.LoginScreen
import com.example.callaguy.presentation.profile.ProfileScreenRoot
import com.example.callaguy.presentation.splashScreen.SplashScreen
import com.example.callaguy.presentation.subService.SubServiceScreenRoot
import dagger.hilt.android.EntryPointAccessors
import kotlinx.serialization.Serializable

@Composable
fun AppNavigationManager() {

    val context = LocalContext.current
    val preferences = EntryPointAccessors
        .fromApplication(context, PreferencesEntryPoint::class.java)
        .getPreferences()
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashScreenRoute
    ) {
        composable<SplashScreenRoute> {
            SplashScreen {
                val token = preferences.getString("jwt" , null)
                if ( token != null) {
                    navController.navigate(ServiceScreenRoute){
                        popUpTo(SplashScreenRoute) { inclusive = true}
                    }
                } else {
                    navController.navigate(LoginScreenRoute) {
                        popUpTo(SplashScreenRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable<LoginScreenRoute> {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(RegisterScreenRoute)
                },
                onNavigateToHome = {
                    navController.navigate(ServiceScreenRoute) {
                        popUpTo(LoginScreenRoute) { inclusive = true}
                    }
                }
            )
        }
        composable<RegisterScreenRoute> {
            RegistrationScreen(
                onNavigateToLogin = {
                    navController.navigate(LoginScreenRoute)
                }
            )
        }
        composable<ServiceScreenRoute> {
            ServiceScreen(
                onCardClick = { id , name ->
                    navController.navigate(
                        SubServiceScreenRoute(id , name)
                    )
                },
                onHomeClick = { navController.navigate(ServiceScreenRoute) },
                onProfileClick = { navController.navigate(ProfileScreenRoute) }
            )
        }
        composable<ProfileScreenRoute> {
            ProfileScreenRoot(
                onLogOut = {
                    preferences.edit { remove("jwt") }
                    navController.navigate(LoginScreenRoute){
                        popUpTo(ServiceScreenRoute){ inclusive = true }
                    }
                },
                onHomeClick = {
                    navController.navigate(ServiceScreenRoute)  {
                        launchSingleTop = true
                    }
                },
                onProfileClick = {
                    navController.navigate(ProfileScreenRoute)  {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<SubServiceScreenRoute> {
            val args = it.toRoute<SubServiceScreenRoute>()
            SubServiceScreenRoot(
                serviceId = args.serviceId,
                serviceName = args.serviceName
            )
        }
    }
}


@Serializable
object SplashScreenRoute

@Serializable
object RegisterScreenRoute

@Serializable
object LoginScreenRoute

@Serializable
object ServiceScreenRoute

@Serializable
object ProfileScreenRoute

@Serializable
data class SubServiceScreenRoute(
    val serviceId : Int,
    val serviceName : String
)