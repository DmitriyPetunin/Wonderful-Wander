package com.android.practise.wonderfulwander.presentation.bottomnav

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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.practise.wonderfulwander.presentation.bottomnav.map.MapScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.photos.PhotosScreen
import com.android.practise.wonderfulwander.presentation.bottomnav.photos.PhotosScreenRoute
import com.example.navigation.ScreenBottomNav
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.ProfileScreenRoute
import com.example.navigation.Screen


@Composable
fun BottomNavScreen(
    navController: NavHostController,
    startRoute: String = ScreenBottomNav.MapScreen.route,
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
            composable(ScreenBottomNav.MapScreen.route) { MapScreenRoute() }

            composable(ScreenBottomNav.PhotosScreen.route) { PhotosScreenRoute() }

            composable(ScreenBottomNav.ProfileScreen.route) {
                ProfileScreenRoute(
                    navigateToAuthScreen = { navigateToAuthScreen(controller = navController) },
                    navigateToFriendsScreen = {navigateToFriendsScreen(controller = navController)},
                    navigateToUpdateScreen = { navigateToUpdateProfileInfoScreen(controller = navController) },
                    navigateToRegisterScreen = {navigateToRegisterScreen(controller = navController)},
                )
            }
        }

    }
}

private fun navigateToAuthScreen(controller: NavController) {
    controller.navigate(Screen.AuthScreen.route) {
        popUpTo(Screen.AuthScreen.route) { inclusive = true } //очистка стека навигации
    }
}

private fun navigateToRegisterScreen(controller: NavController) {
    controller.navigate(Screen.RegisterScreen.route) {
        popUpTo(Screen.AuthScreen.route) { inclusive = true } //очистка стека навигации
    }
}

private fun navigateToFriendsScreen(controller: NavController) {
    controller.navigate(Screen.FriendsScreen.route) {
        popUpTo(Screen.AuthScreen.route) { inclusive = true } //очистка стека навигации
    }
}

private fun navigateToUpdateProfileInfoScreen(controller: NavController) {
    controller.navigate(Screen.UpdateProfileInfoScreen.route) {
        popUpTo(Screen.UpdateProfileInfoScreen.route) { inclusive = true } //очистка стека навигации
    }
}

@Composable
fun MyBottomNavigationBar(navController: NavHostController, currentRoute: String?) {

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
                        launchSingleTop =
                            true // если экран уже находится в верхней части стека навигации, он не будет добавлен повторно.
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