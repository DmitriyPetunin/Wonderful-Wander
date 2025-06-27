package com.example.navigation

sealed class Screen(
    val route: String
) {
    data object AuthScreen : Screen(Routes.AUTH)
    data object WalkScreen : Screen(Routes.WALK)

    data object RegisterScreen : Screen(Routes.REGISTER)
    data object UpdateProfileInfoScreen : Screen(Routes.UPDATE)

    data object PeopleScreen: Screen(Routes.PEOPLE)

    data object CreatePostScreen: Screen(Routes.CREATE_POST)

    data object BottomNavScreen : Screen(route = Routes.BOTTOM)

    data object CreateWalkScreen: Screen(route = Routes.CREATE_WALK)

    data object PersonProfileScreen: Screen(route = Routes.PERSON_PROFILE)

    data object PostDetailScreen: Screen(route = Routes.POST_DETAIL)
}

object Routes {
    const val AUTH = "authScreen"
    const val WALK = "walkScreen"
    const val REGISTER = "registerScreen"
    const val UPDATE = "updateProfileInfoScreen"
    const val PEOPLE = "peopleScreen"
    const val CREATE_POST = "createPostScreen"
    const val BOTTOM = "bottomNavScreen"
    const val CREATE_WALK = "createWalkScreen"
    const val PERSON_PROFILE = "personProfileScreen"
    const val POST_DETAIL = "postDetailScreen"
}