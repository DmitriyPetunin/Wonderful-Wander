package com.android.practise.wonderfulwander.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.practise.wonderfulwander.navigation.ScreenBottomNav
import com.android.practise.wonderfulwander.presentation.profile.ProfileScreen
import com.android.practise.wonderfulwander.presentation.viewmodel.SignInViewModel


@Composable
fun BottomNavScreen(
    navController: NavHostController,
    startRoute: String = ScreenBottomNav.MapScreen.route,
    signInViewModel: SignInViewModel
) {

    val bottomBarNavController = rememberNavController()
    val currentRoute = currentRoute(bottomBarNavController)

    Scaffold(
        bottomBar = {
            MyBottomNavigationBar(
                navController = bottomBarNavController,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomBarNavController,
            startDestination = startRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ScreenBottomNav.MapScreen.route) { MapScreen() }
            composable(ScreenBottomNav.PhotosScreen.route) { PhotosScreen() }
            composable(ScreenBottomNav.ProfileScreen.route) { ProfileScreen(controller = navController, signInViewModel = signInViewModel) }
        }

    }
}

@Composable
fun MyBottomNavigationBar(navController: NavHostController, currentRoute: String?){

    val items = listOf(
        ScreenBottomNav.MapScreen,
        ScreenBottomNav.PhotosScreen,
        ScreenBottomNav.ProfileScreen
    )

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
            .clip(shape = CircleShape.copy(CornerSize(24.dp))),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true // сохранение состояния прокрутки, ввод данных
                        }
                        launchSingleTop = true // если экран уже находится в верхней части стека навигации, он не будет добавлен повторно.
                        restoreState = true // восстановление состояния экрана
                    }
                },
                label = { Text(text = screen.label) },
                icon = { Icon(screen.iconId, contentDescription = "") }
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}