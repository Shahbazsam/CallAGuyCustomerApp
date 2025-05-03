package com.example.callaguy.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    onHomeClick : () -> Unit,
    onProfileClick : () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(
                width = 1.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(24.dp)
                )
            .background(Color(0xFFF5F5F5)),
        shadowElevation = 8.dp
    ) {
        NavigationBar(
            containerColor = Color(0xFFFFFFFF)
        ) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null ,
                        tint = if (selectedIndex == 0) Color.DarkGray else Color(0xFF777777)
                    )
                },
                selected = false ,
                onClick = {
                    selectedIndex = 0
                    onHomeClick()
                },
                colors =  NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color.Gray
                )
            )
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint =if (selectedIndex == 1) Color.DarkGray else Color(0xFF777777)
                    )
                },
                selected = false ,
                onClick = {
                    selectedIndex = 1
                    onProfileClick()
                },
                colors =  NavigationBarItemDefaults.colors(
                    unselectedIconColor = Color(0xFF777777)
                )
            )

        }
    }

}



@Preview(showBackground = true)
@Composable
fun Preview(modifier: Modifier = Modifier) {
    BottomNavigationBar(
        onProfileClick = {},
        onHomeClick = {}
    )
}