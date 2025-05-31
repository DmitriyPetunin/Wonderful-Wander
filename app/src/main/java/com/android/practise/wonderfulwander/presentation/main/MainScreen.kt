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
import com.android.practise.wonderfulwander.presentation.post.UploadPhotoPostScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.PeopleScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.PersonProfileScreenRoute
import com.android.practise.wonderfulwander.presentation.bottomnav.profile.UpdateProfileScreenRoute
import com.android.practise.wonderfulwander.presentation.login.LoginScreenRoute
import com.android.practise.wonderfulwander.presentation.registration.RegistrationScreenRoute
import com.example.navigation.Screen
import com.example.navigation.ScreenBottomNav
import com.example.presentation.viewmodel.CreatePostViewModel


@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val createPostViewModel: CreatePostViewModel = hiltViewModel()

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
        createWalkScreen = { CreateWalkPageRoute() },
        createPostScreen = {
            CreatePostScreenRoute(
                createPostViewModel,
                navigateToUploadImageScreen = { navigateToUploadPhotoScreen(controller = navController) })
        },
        peopleScreen = { listType ->
            PeopleScreenRoute(
                listType = listType,
                navigateToPeoplePerson = { userId: String ->
                    navigateToPersonProfile(
                        controller = navController,
                        id = userId
                    )
                })
        },
        uploadPhotoScreen = { UploadPhotoPostScreenRoute(createPostViewModel) },
        personProfile = { id -> PersonProfileScreenRoute(userId = id) }
    )
}

private fun navigateToPersonProfile(controller: NavController, id: String) {
    controller.navigate("${Screen.PersonProfileScreen.route}/$id")
}


private fun navigateToUploadPhotoScreen(controller: NavController) {
    controller.navigate(Screen.UploadPhotoScreen.route)
}

private fun navigateToProfileScreen(controller: NavController) {
    controller.navigate(Screen.BottomNavScreen.route + "/${ScreenBottomNav.ProfileScreen.route}")
}

private fun navigateToMapScreen(controller: NavController) {
    controller.navigate(Screen.BottomNavScreen.route + "/${ScreenBottomNav.MapScreen.route}")
}