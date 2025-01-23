package com.ovan.raionbattlepass.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ovan.raionbattlepass.presentation.auth.LoginScreen
import com.ovan.raionbattlepass.presentation.auth.RegisterScreen
import com.ovan.raionbattlepass.presentation.bottomnavbar.BottomNavigationBar
import com.ovan.raionbattlepass.presentation.bottomnavbar.NavHostContainer
import com.ovan.raionbattlepass.presentation.friendlistpage.FriendListScreen
import com.ovan.raionbattlepass.presentation.profile.PostDetailScreen
import com.ovan.raionbattlepass.presentation.profile.UpdateUsernameScreen
import com.ovan.raionbattlepass.ui.theme.RAIONBattlePassTheme
import androidx.datastore.preferences.core.Preferences
import com.ovan.raionbattlepass.util.DataStoreManager

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class MainActivity : ComponentActivity() {
    lateinit var dataStoreManager: DataStoreManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        dataStoreManager = DataStoreManager(dataStore)
        super.onCreate(savedInstanceState)
        setContent {
            RAIONBattlePassTheme {
                
                val navController = rememberNavController()
                NavHost(
                    navController = navController, startDestination = "login"
                ) {
                    composable("login") { LoginScreen(navController, dataStoreManager) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") {
                        Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) { padding ->
                            NavHostContainer(
                                navController = navController,
                                padding = padding,
                                dataStoreManager = dataStoreManager
                            )
                        }
                    }
                    composable("post/{postId}") { backStackEntry ->
                        val postId = backStackEntry.arguments?.getString("postId")
                        PostDetailScreen(navController, id = postId!!)
                    }
                    composable("friendlist") { FriendListScreen(navController) }
                    composable("updateusername") {
                        UpdateUsernameScreen(
                            navController, dataStoreManager
                        )
                    }
                }
            }
        }
    }
}