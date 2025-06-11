package com.example.callaguy


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.callaguy.data.dto.supportTicket.SupportTicketStatus
import com.example.callaguy.domain.model.GetServiceRequestModel
import com.example.callaguy.presentation.Service.ServiceScreen
import com.example.callaguy.presentation.Service.ServiceScreenRoot
import com.example.callaguy.presentation.auth.RegistrationScreen
import com.example.callaguy.presentation.getServiceRequests.GetDetailedScreen
import com.example.callaguy.presentation.getServiceRequests.GetOrderRoot
import com.example.callaguy.presentation.login.LoginScreen
import com.example.callaguy.presentation.navigation.CustomNavType
import com.example.callaguy.presentation.navigation.Destinations
import com.example.callaguy.presentation.profile.ProfileScreenRoot
import com.example.callaguy.presentation.serviceRequest.ServiceRequestScreen
import com.example.callaguy.presentation.splashScreen.SplashScreen
import com.example.callaguy.presentation.subService.SubServiceScreenRoot
import com.example.callaguy.presentation.supportMessage.SupportMessageScreen
import com.example.callaguy.presentation.supportTicket.CreateSupportTicketRoot
import com.example.callaguy.presentation.supportTicket.TicketsScreenRoot
import dagger.hilt.android.EntryPointAccessors
import kotlin.reflect.typeOf


@Composable
fun AppNavigationManager(
    navController: NavHostController,
    modifier: Modifier
) {

    val context = LocalContext.current
    val preferences = EntryPointAccessors
        .fromApplication(context, PreferencesEntryPoint::class.java)
        .getPreferences()


    NavHost(
        navController = navController,
        startDestination = Destinations.SplashScreenRoute,
        modifier = modifier
    ) {
        composable<Destinations.SplashScreenRoute> {
            SplashScreen {
                val token = preferences.getString("jwt", null)
                if (token != null) {
                    navController.navigate(Destinations.ServiceScreenRoute) {
                        popUpTo(Destinations.SplashScreenRoute) { inclusive = true }
                    }
                } else {
                    navController.navigate(Destinations.LoginScreenRoute) {
                        popUpTo(Destinations.SplashScreenRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        composable<Destinations.LoginScreenRoute> {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Destinations.RegisterScreenRoute)
                },
                onNavigateToHome = {
                    navController.navigate(Destinations.ServiceScreenRoute) {
                        popUpTo(Destinations.LoginScreenRoute) { inclusive = true }
                    }
                }
            )
        }
        composable<Destinations.RegisterScreenRoute> {
            RegistrationScreen(
                onNavigateToLogin = {
                    navController.navigate(Destinations.LoginScreenRoute)
                }
            )
        }
        composable<Destinations.ServiceScreenRoute> {
            ServiceScreenRoot(
                onCardClick = { id, name ->
                    navController.navigate(
                        Destinations.SubServiceScreenRoute(id, name)
                    )
                }
            )
        }
        composable<Destinations.ProfileScreenRoute> {
            ProfileScreenRoot(
                onLogOut = {
                    preferences.edit { remove("jwt") }
                    navController.navigate(Destinations.LoginScreenRoute) {
                        popUpTo(Destinations.ServiceScreenRoute) { inclusive = true }
                    }
                },
            )
        }
        composable<Destinations.SubServiceScreenRoute> {
            val args = it.toRoute<Destinations.SubServiceScreenRoute>()
            SubServiceScreenRoot(
                onCardClick = { id, name , image ->
                    navController.navigate(
                        Destinations.ServiceRequestRoute(
                            subServiceId = id,
                            subServiceName = name,
                            subServiceImage = image
                        )
                    )
                },
                serviceId = args.serviceId,
                serviceName = args.serviceName
            )
        }
        composable<Destinations.ServiceRequestRoute> {
            val args = it.toRoute<Destinations.ServiceRequestRoute>()
            ServiceRequestScreen(
                onGoToOrders = {
                    navController.navigate(Destinations.OrdersScreenRoute){
                    popUpTo(Destinations.ServiceScreenRoute)
                }
                               },
                subServiceId = args.subServiceId,
                subServiceName = args.subServiceName,
                subServiceImage = args.subServiceImage,
            )
        }

        composable<Destinations.OrdersScreenRoute> {
            GetOrderRoot(
                onCardClick = { order ->
                    navController.navigate(
                        Destinations.DetailedOrderRoute(
                            order = order
                        )
                    )
                }
            )
        }
        composable<Destinations.DetailedOrderRoute>(
            typeMap = mapOf(
                typeOf<GetServiceRequestModel>() to CustomNavType.orderType,
            )
        ) {
            val args = it.toRoute<Destinations.DetailedOrderRoute>()
            GetDetailedScreen(
                onRaise = { serviceRequestId ->
                    navController.navigate(Destinations.CreateSupportScreenRoute(serviceRequestId))
                },
                order = args.order
            )
        }
        composable<Destinations.CreateSupportScreenRoute> {
            val args = it.toRoute<Destinations.CreateSupportScreenRoute>()
            CreateSupportTicketRoot(
                serviceRequestId = args.serviceRequestId,
                onGoToTickets = {
                    navController.navigate(Destinations.TicketsScreenRoute){
                        popUpTo(Destinations.ServiceScreenRoute)
                    }
                }
            )
        }
        composable<Destinations.TicketsScreenRoute> {
            TicketsScreenRoot(
                onCardClick = { id , status ->
                    navController.navigate(Destinations.SupportMessageScreenRoute(id , status))
                }
            )
        }
        composable<Destinations.SupportMessageScreenRoute>(
            typeMap = mapOf(
                typeOf<SupportTicketStatus>() to NavType.EnumType<SupportTicketStatus>(SupportTicketStatus::class.java)
            )
        ) {
            val args = it.toRoute<Destinations.SupportMessageScreenRoute>()
            SupportMessageScreen(
                ticketId = args.ticketId,
                status = args.status
            )
        }
    }
}


