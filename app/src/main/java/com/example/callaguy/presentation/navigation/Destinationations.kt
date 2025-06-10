package com.example.callaguy.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.callaguy.data.dto.supportTicket.SupportTicketStatus
import com.example.callaguy.domain.model.GetServiceRequestModel
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
        val order: GetServiceRequestModel
    ) : Destinations()

    @Serializable
    data class SubServiceScreenRoute(
        val serviceId: Int,
        val serviceName: String
    ) : Destinations()

    @Serializable
    data class ServiceRequestRoute(
        val subServiceId: Int,
        val subServiceName: String,
        val subServiceImage : String
    ) : Destinations()

    @Serializable
    data class CreateSupportScreenRoute(
        val serviceRequestId: Int
    ) : Destinations()

    @Serializable
    data object TicketsScreenRoute : Destinations()

    @Serializable
    data class SupportMessageScreenRoute(
        val ticketId: Int,
        val status: SupportTicketStatus
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
    Tickets(
        label = "Tickets",
        selectedIcon = Icons.Filled.Message,
        unselectedIcon = Icons.Outlined.Message,
        route = Destinations.TicketsScreenRoute
    ),
    Profile(
        label = "Profile",
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        route = Destinations.ProfileScreenRoute
    )
}