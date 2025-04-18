package com.android.practise.wonderfulwander.presentation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.android.practise.wonderfulwander.navigation.AppNavGraph
import com.android.practise.wonderfulwander.sign_in.SignInViewModel



@Composable
fun MainScreen(
    viewModel: SignInViewModel
) {
    val navController = rememberNavController()

    AppNavGraph(
        navHostController = navController,
        profileScreen = { ProfileScreen(signInViewModel = viewModel, controller = navController) },
        authScreen = { LoginScreen(signInViewModel = viewModel,controller = navController) },
    )
}