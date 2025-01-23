package com.ovan.raionbattlepass.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem("Home", Icons.Filled.Home, "home"),
        BottomNavItem("Search", Icons.Filled.Search, "search"),
        BottomNavItem("Profile", Icons.Filled.Person, "profile")
    )
}