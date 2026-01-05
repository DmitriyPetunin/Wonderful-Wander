package com.android.practise.wonderfulwander.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.feature.walk.impl.ui.CreateWalkScreenRoute
import com.example.navigation.AppNavGraph
import com.example.feature.walk.impl.ui.WalkScreenRoute
import com.example.feature.post.impl.ui.CreatePostScreenRoute
import com.example.feature.profile.impl.ui.PeopleScreenRoute
import com.example.feature.profile.impl.ui.PersonProfileScreenRoute
import com.example.feature.profile.impl.ui.UpdateProfileScreenRoute
import com.example.feature.auth.impl.ui.LoginScreenRoute
import com.example.feature.post.impl.ui.PostDetailInfoScreenRoute
import com.example.feature.auth.impl.ui.RegistrationScreenRoute
import com.example.navigation.Screen
import com.example.navigation.ScreenBottomNav


@Composable
fun MainScreen(
    modifier: Modifier
) {
    val navController = rememberNavController()

    AppNavGraph(
        startDestination = Screen.BottomNavScreen.route,
        navHostController = navController,
        authScreen = {
            LoginScreenRoute(navigateToProfile = { navigateToProfileScreen(navController) })
        },
        bottomNavScreen = { startRoute ->
            BottomNavScreen(
                navController = navController,
                startRoute = startRoute,
            )
        },
        walkScreen = { WalkScreenRoute() },
        registerScreen = { RegistrationScreenRoute(onButtonClick = { navigateToMapScreen(controller = navController) }) },
        updateProfileScreen = {
            UpdateProfileScreenRoute(navigateToProfile = {
                navigateToProfileScreen(
                    controller = navController
                )
            })
        },
        createWalkScreen = { CreateWalkScreenRoute() },
        createPostScreen = { CreatePostScreenRoute(navigateToPhotosScreen = {navigateToPhotosScreen(controller = navController)}) },
        peopleScreen = { listType ->
            PeopleScreenRoute(
                listType = listType,
                navigateToPersonProfile = { userId: String ->
                    navigateToPersonProfile(
                        controller = navController,
                        userId = userId
                    )
                })
        },
        personProfile = { id -> PersonProfileScreenRoute(userId = id) },
        postDetailScreen = { id -> PostDetailInfoScreenRoute(
            postId = id,
            navigateToPersonProfile = { userId -> navigateToPersonProfile(controller = navController, userId = userId)}
        ) },
    )
}

private fun navigateToPersonProfile(controller: NavController, userId: String) {
    controller.navigate("${Screen.PersonProfileScreen.route}/$userId")
}

private fun navigateToProfileScreen(controller: NavController) {
    controller.navigate(Screen.BottomNavScreen.route + "/${ScreenBottomNav.ProfileScreen.route}")
}

private fun navigateToMapScreen(controller: NavController) {
    controller.navigate(Screen.BottomNavScreen.route + "/${ScreenBottomNav.MapScreen.route}")
}

private fun navigateToPhotosScreen(controller: NavController) {
    controller.navigate(Screen.BottomNavScreen.route + "/${ScreenBottomNav.PostsScreen.route}")
}