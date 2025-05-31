package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


@Composable
fun AppNavGraph(
    startDestination: String,
    navHostController: NavHostController,
    authScreen: @Composable () -> Unit,
    registerScreen: @Composable () -> Unit,
    walkScreen: @Composable () -> Unit,
    bottomNavScreen: @Composable (String) -> Unit,
    updateProfileScreen: @Composable () -> Unit,
    createWalkScreen: @Composable () -> Unit,
    createPostScreen: @Composable () -> Unit,
    peopleScreen: @Composable (String) -> Unit,
    uploadPhotoScreen: @Composable () -> Unit,
    personProfile: @Composable (String) -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = Screen.AuthScreen.route) {
            authScreen()
        }
        composable(route = Screen.WalkScreen.route) {
            walkScreen()
        }
        composable(route = Screen.UpdateProfileInfoScreen.route) {
            updateProfileScreen()
        }
        composable(route = Screen.RegisterScreen.route) {
            registerScreen()
        }
        composable(route = Screen.CreatePostScreen.route) {
            createPostScreen()
        }
        composable(route = Screen.BottomNavScreen.route) {
            bottomNavScreen(ScreenBottomNav.ProfileScreen.route)
        }
        composable(route = Screen.CreateWalkScreen.route) {
            createWalkScreen()
        }
        composable(route = Screen.UploadPhotoScreen.route){
            uploadPhotoScreen()
        }
        composable(
            route = "${Screen.PersonProfileScreen.route}/{userId}",
            arguments = listOf(navArgument("userId"){
                defaultValue = ""
            })
        ){ backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            personProfile(userId?:"")
        }
        composable(
            route = "${Screen.PeopleScreen.route}/{listType}",
            arguments = listOf(navArgument("listType") {
                defaultValue = "friends"
            })
        ) { backStackEntry ->
            val listType = backStackEntry.arguments?.getString("listType")
            peopleScreen(listType ?: "friends")
        }

        composable(
            route = "${Screen.BottomNavScreen.route}/{startRoute}",
            arguments = listOf(navArgument("startRoute") {
                defaultValue = ScreenBottomNav.MapScreen.route
            })
        ) { backStackEntry ->
            val startRoute = backStackEntry.arguments?.getString("startRoute")
            bottomNavScreen(startRoute ?: ScreenBottomNav.MapScreen.route)
        }
    }
}