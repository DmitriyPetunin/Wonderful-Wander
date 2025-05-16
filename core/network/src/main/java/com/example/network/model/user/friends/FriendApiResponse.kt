package com.example.network.model.user.friends





data class FriendApiResponse(
    val listOfFriends:List<FriendApi>?,
    val timestamp:String?,
    val error:String?,
    val message:String?
)

data class FriendApi (

    val userId:String?,
    val username:String?,
    val firstname:String?,
    val lastname:String?,
    val bio:String?,
    val avatarUrl:String?,
    val followersCount:Int?,
    val followingCount:Int?,
    val friendsCount:Int?
)