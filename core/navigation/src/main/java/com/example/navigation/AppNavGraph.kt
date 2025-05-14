package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@Composable
fun AppNavGraph(
    startDestination:String,
    navHostController: NavHostController,
    authScreen: @Composable () -> Unit,
    walkScreen: @Composable () -> Unit,
    bottomNavScreen: @Composable (String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
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