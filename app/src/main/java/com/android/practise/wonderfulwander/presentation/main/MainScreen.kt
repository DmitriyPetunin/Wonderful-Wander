package com.android.practise.wonderfulwander.presentation.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.navigation.AppNavGraph
import com.android.practise.wonderfulwander.presentation.WalkScreen
import com.android.practise.wonderfulwander.presentation.WalkScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.BottomNavScreen
import com.android.practise.wonderfulwander.presentation.login.LoginScreen
import com.android.practise.wonderfulwander.presentation.login.LoginScreenRoute
import com.android.practise.wonderfulwander.presentation.registration.RegistrationScreen
import com.android.practise.wonderfulwander.presentation.registration.RegistrationScreenRoute
import com.example.navigation.Screen
import com.example.navigation.ScreenBottomNav
import com.example.presentation.viewmodel.SignInViewModel


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    AppNavGraph(
        startDestination = Screen.AuthScreen.route,
        navHostController = navController,
        authScreen = {
            LoginScreenRoute(onNavigateToProfile = { navigateToProfileScreen(navController) })
        },
        bottomNavScreen = { startRoute ->
            BottomNavScreen(
                navController = navController,
                startRoute = startRoute,
            )
        },
        walkScreen = { WalkScreenRoute() },
        registerScreen = { RegistrationScreenRoute(onButtonClick = { navigateToMapScreen(controller = navController) }) }
    )
}

private fun navigateToProfileScreen(controller: NavController) {
    controller.navigate(Screen.BottomNavScreen.route + "/${ScreenBottomNav.ProfileScreen.route}") {
        popUpTo(Screen.AuthScreen.route) { inclusive = true } //очистка стека навигации
    }
}

private fun navigateToMapScreen(controller: NavController) {
    controller.navigate(Screen.BottomNavScreen.route + "/${ScreenBottomNav.MapScreen.route}") {
        popUpTo(Screen.AuthScreen.route) { inclusive = true } //очистка стека навигации
    }
}