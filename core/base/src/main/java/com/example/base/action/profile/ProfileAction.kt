package com.example.base.action.profile

sealed class ProfileAction {

    data object SubmitGetAllFriends: ProfileAction()
    data object SignOut: ProfileAction()
}