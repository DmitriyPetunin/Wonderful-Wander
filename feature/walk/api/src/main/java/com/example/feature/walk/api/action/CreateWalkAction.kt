package com.example.feature.walk.api.action

import com.example.domain.model.user.People
import com.example.domain.model.walk.Point


sealed class CreateWalkAction {

    data object GetAllFriends: CreateWalkAction()
    data object SubmitSaveWalk: CreateWalkAction()

    data class UpdateQueryParam(val input: String): CreateWalkAction()
    data class UpdateStartPoint(val data: Point): CreateWalkAction()

    data class AddFriend(val friend: People):CreateWalkAction()
}