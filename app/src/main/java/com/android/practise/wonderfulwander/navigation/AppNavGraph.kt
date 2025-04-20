package com.android.practise.wonderfulwander.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    authScreen: @Composable () -> Unit,
    walkScreen: @Composable () -> Unit,
    bottomNavScreen: @Composable (String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthScreen.route
    ){
        composable(route = Screen.AuthScreen.route){
            authScreen()
        }
        composable(route = Screen.WalkScreen.route){
            walkScreen()
        }

        composable(
            route = "${Screen.BottomNavScreen.route}/{startRoute}",
            arguments = listOf(navArgument("startRoute") { defaultValue = ScreenBottomNav.MapScreen.route})
        ){ backStackEntry ->
            val startRoute = backStackEntry.arguments?.getString("startRoute")
            bottomNavScreen(startRoute ?: ScreenBottomNav.MapScreen.route)
        }
    }
}