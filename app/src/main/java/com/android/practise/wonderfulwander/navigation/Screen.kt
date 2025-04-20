package com.android.practise.wonderfulwander.navigation

sealed class Screen(
    val route:String
) {
    object AuthScreen:Screen("authScreen")
    object WalkScreen:Screen("walkScreen")

    object BottomNavScreen:Screen(route = "bottomNavScreen")
}