package com.android.practise.wonderfulwander.navigation

sealed class Screen(
    val route:String
) {
    object ProfileScreen:Screen("profileScreen")
    object AuthScreen:Screen("authScreen")

    object eScreen:Screen("eScreen")
}