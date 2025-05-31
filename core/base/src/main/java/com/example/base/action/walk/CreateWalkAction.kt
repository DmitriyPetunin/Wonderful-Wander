package com.example.base.action.walk

sealed class CreateWalkAction {

    data object GetAllFriends: CreateWalkAction()

    data class UpdateQueryParam(val input: String): CreateWalkAction()
}