package com.example.base.event.profile

sealed interface ProfileEvent {
    data object NavigateToAuthPage: ProfileEvent
    data object NavigateToRegisterPage: ProfileEvent
    data object NavigateToFriendsPage: ProfileEvent
    data object NavigateToFollowersPage: ProfileEvent
    data object NavigateToFollowingPage: ProfileEvent
    data object NavigateToUpdateScreenPage: ProfileEvent


    data object UpdateProfileInfo: ProfileEvent

    data class ShowError(val message: String) : ProfileEvent
    data class NavigateToPostDetail(val postId:String):ProfileEvent
}