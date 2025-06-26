package com.android.practise.wonderfulwander.presentation.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.practise.wonderfulwander.presentation.walk.CreateWalkScreenRoute
import com.example.navigation.AppNavGraph
import com.android.practise.wonderfulwander.presentation.walk.WalkScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.BottomNavScreen
import com.android.practise.wonderfulwander.presentation.post.CreatePostScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.PeopleScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.another.PersonProfileScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.me.UpdateProfileScreenRoute
import com.android.practise.wonderfulwander.presentation.login.LoginScreenRoute
import com.android.practise.wonderfulwander.presentation.post.PostDetailInfoScreenRoute
import com.android.practise.wonderfulwander.presentation.registration.RegistrationScreenRoute
import com.android.practise.wonderfulwander.presentation.walk.StartingPointScreenRoute
import com.example.navigation.Screen
import com.example.navigation.ScreenBottomNav


@Composable
fun MainScreen() {
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
        startPointScreen = { StartingPointScreenRoute() }
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