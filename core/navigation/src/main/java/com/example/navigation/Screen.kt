package com.example.navigation

sealed class Screen(
    val route: String
) {
    data object AuthScreen : Screen("authScreen")
    data object WalkScreen : Screen("walkScreen")

    data object RegisterScreen : Screen("registerScreen")
    data object UpdateProfileInfoScreen : Screen("updateProfileInfoScreen")

    data object FriendsScreen: Screen("friendsScreen")

    data object BottomNavScreen : Screen(route = "bottomNavScreen")
}