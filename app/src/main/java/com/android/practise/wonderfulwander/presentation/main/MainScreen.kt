package com.android.practise.wonderfulwander.presentation.main

//import com.android.practise.wonderfulwander.presentation.WalkScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.android.practise.wonderfulwander.navigation.AppNavGraph
import com.android.practise.wonderfulwander.presentation.BottomNavScreen
import com.android.practise.wonderfulwander.presentation.login.LoginScreen
import com.android.practise.wonderfulwander.presentation.viewmodel.SignInViewModel


@Composable
fun MainScreen(
    viewModel: SignInViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    AppNavGraph(
        navHostController = navController,
        authScreen = { LoginScreen(signInViewModel = viewModel,controller = navController) },
        bottomNavScreen = { startRoute -> BottomNavScreen(navController = navController, startRoute = startRoute, signInViewModel = viewModel) },
        walkScreen = {}
    )
}