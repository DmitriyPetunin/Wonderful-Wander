package com.example.base.action.profile

sealed class FriendsPageAction {
    data class SubmitFriendsItem(val userId: String): FriendsPageAction()
    data object SubmitBackButton: FriendsPageAction()
}