package com.example.network.model.user.people

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonProfileInfoResponse(
    val userId: String,

    @SerialName("username")
    val userName: String,

    val firstname: String,
    val lastname: String,

    val bio: String,
    val avatarUrl: String?,

    val followersCount: Int,
    val followingCount: Int,
    val friendsCount: Int,

    val isFollowedByUser:Boolean, //подписан ли я пользователя с userID
    val isFollowingByUser:Boolean, // подписан ли пользователь на меня
    val isFriends:Boolean, // му друзья

)