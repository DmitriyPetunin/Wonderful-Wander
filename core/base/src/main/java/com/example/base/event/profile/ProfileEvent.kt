package com.example.base.event.profile

sealed class ProfileEvent {
    data object NavigateToAuthPage: ProfileEvent()
    data object NavigateToFriendsPage: ProfileEvent()
}