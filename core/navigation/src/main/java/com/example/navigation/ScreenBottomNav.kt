package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class ScreenBottomNav(
    val route:String,
    val label:String,
    val iconId:ImageVector
) {
    object MapScreen: ScreenBottomNav(route = "mapScreen", label = "map", iconId = Icons.Default.LocationOn)
    object PhotosScreen: ScreenBottomNav(route = "photosScreen", label = "photos", iconId = Icons.Default.DateRange)
    object ProfileScreen: ScreenBottomNav(route = "profileScreen", label = "profile", iconId = Icons.Default.AccountCircle)
}