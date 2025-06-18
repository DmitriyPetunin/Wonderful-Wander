package com.android.practise.wonderfulwander.presentation.main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.practise.wonderfulwander.presentation.walk.CreateWalkPageRoute
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
import com.example.navigation.Screen
import com.example.navigation.ScreenBottomNav
import com.example.presentation.viewmodel.CreatePostViewModel


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    AppNavGraph(
        startDestination = Screen.AuthScreen.route,
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
        createWalkScreen = { CreateWalkPageRoute() },
        createPostScreen = { CreatePostScreenRoute() },
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
        postDetailScreen = { id -> PostDetailInfoScreenRoute(postId = id) }
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