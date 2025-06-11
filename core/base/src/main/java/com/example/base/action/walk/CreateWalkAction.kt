package com.example.base.action.walk

import com.example.base.model.user.People

sealed class CreateWalkAction {

    data object GetAllFriends: CreateWalkAction()

    data class UpdateQueryParam(val input: String): CreateWalkAction()

    data class AddFriend(val friend:People):CreateWalkAction()
}