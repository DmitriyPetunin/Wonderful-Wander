package com.example.base.action.profile

sealed class ProfileAction {

    data object SubmitGetAllFriends: ProfileAction()
    data object SubmitGetAllSubscriptions: ProfileAction()
    data object SubmitGetAllSubscribers: ProfileAction()
    data object SignOut: ProfileAction()
    data object SubmitUpdateProfileInfo: ProfileAction()
    data object SubmitDeleteProfile: ProfileAction()
}