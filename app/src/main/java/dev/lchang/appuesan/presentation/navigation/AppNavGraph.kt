package dev.lchang.appuesan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.lchang.appuesan.presentation.auth.LoginScreen
import dev.lchang.appuesan.presentation.auth.RegisterScreen
import dev.lchang.appuesan.presentation.home.HomeScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()

    NavHost(navController = navController,
            startDestination = "login")
    {
        composable("register") { RegisterScreen() }
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen() }
    }

}