package com.example.domain.model.user.profile

class PersonProfileInfoResult (
    val userId: String,
    val userName:String,

    val firstname: String,
    val lastname: String,

    val bio: String,
    val avatarUrl: String,

    val followersCount: Int,
    val followingCount: Int,
    val friendsCount: Int,

    val isFollowedByUser:Boolean, //подписан ли я пользователя с userID
    val isFollowingByUser:Boolean, // подписан ли пользователь на меня
    val isFriends:Boolean, // му друзья
)