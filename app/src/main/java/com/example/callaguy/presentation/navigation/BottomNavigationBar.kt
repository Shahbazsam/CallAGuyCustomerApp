package com.example.callaguy.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlin.reflect.KClass

@Composable
fun BottomNavBar(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .wrapContentHeight()
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .border(
                    width = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(24.dp)
                ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    selectedIndex = 0
                    onHomeClick()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = if (selectedIndex == 0) Color.DarkGray else Color(0xFF777777),
                    modifier = Modifier.size(24.dp)
                )
            }

            IconButton(
                onClick = {
                    selectedIndex = 1
                    onProfileClick()
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Profile",
                    tint = if (selectedIndex == 1) Color.DarkGray else Color(0xFF777777),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


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
                            navController.navigate(bottomNavigationItem.route)
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

@Composable
fun BottomBar(
    currentRoute: KClass<out Destinations>,
    onTabClick: (Destinations) -> Unit
) {
    BottomAppBar(
        containerColor = Color(0xFFF7FAFC)
    ) {
        TopLevelDestinations.entries.forEach { item ->
            // Correct comparison - compare the actual route objects
            val isSelected = item.route::class == currentRoute

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
                onClick = { onTabClick(item.route) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun preview(modifier: Modifier = Modifier) {
    /*BottomNavBar(
        onProfileClick = {},
        onHomeClick = {}
    )*/
    BottomBar(
        currentRoute = Destinations.ProfileScreenRoute::class
    ) { }
}
