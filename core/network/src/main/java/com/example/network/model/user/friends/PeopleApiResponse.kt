package com.example.network.model.user.friends

import kotlinx.serialization.Serializable

@Serializable
data class PeopleApiResponse(
    val listOfFriends: List<FriendApi>?,
    val timestamp: String?,
    val error: String?,
    val message: String?
)
@Serializable
data class FriendApi(

    val userId: String?,
    val username: String?,
    val firstname: String?,
    val lastname: String?,
    val bio: String?,
    val avatarUrl: String?,
    val followersCount: Int?, // твои подписчики (подписчики)
    val followingCount: Int?, //ты подписался (подписки)
    val friendsCount: Int?
)