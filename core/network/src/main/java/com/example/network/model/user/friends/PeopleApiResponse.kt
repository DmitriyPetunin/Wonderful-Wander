package com.example.network.model.user.friends

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeopleApiResponse(
    @SerialName("data")
    val listOfFriends: List<PeopleApi>?,
    val total:Int?,
    val limit:Int?,
    val offset:Int?,
//    val timestamp: String?,
//    val error: String?,
//    val message: String?
)
@Serializable
data class PeopleApi(
    val userId:String,
    @SerialName("username")
    val userName:String,
    val avatarUrl:String?
)
@Serializable
data class FriendApi(

    val userId: String?,
    @SerialName("username")
    val userName: String?,
    val firstname: String?,
    val lastname: String?,
    val bio: String?,
    val avatarUrl: String?,
    val followersCount: Int?, // твои подписчики (подписчики)
    val followingCount: Int?, //ты подписался (подписки)
    val friendsCount: Int?
)