package com.example.base.event.profile

sealed interface ProfileEvent {
    data object NavigateToAuthPage: ProfileEvent
    data object NavigateToFriendsPage: ProfileEvent
}