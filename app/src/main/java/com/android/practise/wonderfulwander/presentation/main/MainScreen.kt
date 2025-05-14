package com.android.practise.wonderfulwander.presentation.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.navigation.AppNavGraph
import com.android.practise.wonderfulwander.presentation.WalkScreen
import com.android.practise.wonderfulwander.presentation.bottomnav.BottomNavScreen
import com.android.practise.wonderfulwander.presentation.login.LoginScreen
import com.example.navigation.Screen
import com.example.presentation.viewmodel.SignInViewModel


@Composable
fun MainScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    AppNavGraph(
        startDestination = Screen.AuthScreen.route,
        navHostController = navController,
        authScreen = { LoginScreen(signInViewModel = viewModel, onButtonClick = {navigateToProfileScreen(controller = navController)}) },
        bottomNavScreen = { startRoute ->
            BottomNavScreen(
                navController = navController,
                startRoute = startRoute,
                signInViewModel = viewModel
            )
        },
        walkScreen = { WalkScreen() }
    )
}
fun navigateToProfileScreen(controller:NavController){
    controller.navigate(Screen.BottomNavScreen.route + "/${com.example.navigation.ScreenBottomNav.ProfileScreen.route}") {
        popUpTo(Screen.AuthScreen.route) { inclusive = true } //очистка стека навигации
    }
}