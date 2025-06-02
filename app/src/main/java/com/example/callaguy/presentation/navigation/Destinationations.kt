package com.example.callaguy.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.callaguy.domain.model.GetServiceRequestModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


sealed class Destinations {
    @Serializable
    object SplashScreenRoute : Destinations()

    @Serializable
    object RegisterScreenRoute : Destinations()

    @Serializable
    object LoginScreenRoute : Destinations()

    @Serializable
    object ServiceScreenRoute : Destinations()

    @Serializable
    object ProfileScreenRoute : Destinations()

    @Serializable
    object OrdersScreenRoute : Destinations()

    @Serializable
    data class DetailedOrderRoute(
        val order : GetServiceRequestModel
    ) : Destinations()

    @Serializable
    data class SubServiceScreenRoute(
        val serviceId : Int,
        val serviceName : String
    ) : Destinations()

    @Serializable
    data class ServiceRequestRoute(
        val subServiceId : Int ,
        val subServiceName : String,
        //val subServiceImage : String
    ) : Destinations()
}

enum class TopLevelDestinations(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: Destinations,
) {
    Services(
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Destinations.ServiceScreenRoute
    ),
    Orders(
    label = "My Orders",
    selectedIcon = Icons.Filled.EventNote,
    unselectedIcon = Icons.Outlined.EventNote,
    route = Destinations.OrdersScreenRoute
    ),
    Profile(
    label = "Profile",
    selectedIcon = Icons.Filled.AccountCircle,
    unselectedIcon = Icons.Outlined.AccountCircle,
    route = Destinations.ProfileScreenRoute
    )
}