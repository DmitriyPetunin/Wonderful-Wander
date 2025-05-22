package com.example.base.event.profile

sealed interface ProfileEvent {
    data object NavigateToAuthPage: ProfileEvent
    data object NavigateToRegisterPage: ProfileEvent
    data object NavigateToFriendsPage: ProfileEvent
    data object NavigateToSubscriptionsPage: ProfileEvent
    data object NavigateToSubscribersPage: ProfileEvent
    data object NavigateToUpdateScreenPage: ProfileEvent

    data object UpdateProfileInfo: ProfileEvent

    data class ShowError(val message: String) : ProfileEvent
}