package com.example.callaguy.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBar(navController: NavController) {

    val navBackStack by navController.currentBackStackEntryAsState()
    val currentDestination : NavDestination? = navBackStack?.destination

    val showBottomNav = TopLevelDestinations.entries.map { it.route::class }.any { route ->
        currentDestination?.hierarchy?.any {
            it.hasRoute(route)
        } == true
    }

    AnimatedVisibility(visible = showBottomNav) {
        BottomAppBar(
            containerColor = Color(0xFFF7FAFC)
        ) {
            TopLevelDestinations.entries.map { bottomNavigationItem ->

                val isSelected = currentDestination?.hierarchy?.any {
                    it.hasRoute(bottomNavigationItem.route::class)
                } == true

                if (currentDestination != null) {
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.DarkGray,
                            unselectedIconColor = Color(0xFF4A739C),
                            selectedTextColor = Color.DarkGray,
                            unselectedTextColor = Color(0xFF4A739C),
                            indicatorColor = Color.Transparent,
                            disabledIconColor = Color.LightGray.copy(alpha = 0.5f),
                            disabledTextColor = Color.LightGray.copy(alpha = 0.5f)
                        ),
                        selected = isSelected,
                        onClick = {
                            navController.navigate(bottomNavigationItem.route){
                                popUpTo(Destinations.ServiceScreenRoute){
                                    inclusive = false
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) bottomNavigationItem.selectedIcon else bottomNavigationItem.unselectedIcon,
                                contentDescription = bottomNavigationItem.label
                            )
                        },
                        alwaysShowLabel = isSelected,
                        label = {
                            Text(bottomNavigationItem.label)
                        }
                    )
                }
            }
        }
    }
}
