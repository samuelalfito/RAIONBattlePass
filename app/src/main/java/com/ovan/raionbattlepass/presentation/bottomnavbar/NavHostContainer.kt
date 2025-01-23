package com.ovan.raionbattlepass.presentation.bottomnavbar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ovan.raionbattlepass.presentation.homepage.HomeScreen
import com.ovan.raionbattlepass.presentation.profile.ProfileScreen
import com.ovan.raionbattlepass.presentation.search.SearchScreen
import com.ovan.raionbattlepass.util.DataStoreManager

@Composable
fun NavHostContainer(navController: NavHostController, padding: PaddingValues, dataStoreManager: DataStoreManager) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = Modifier.padding(padding)
    ) {
        composable("home") { HomeScreen() }
        composable("search") { SearchScreen(navController) }
        composable("profile") { ProfileScreen(navController, dataStoreManager) }
    }
}