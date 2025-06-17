package com.example.base.state

import android.net.Uri

data class ProfileState (
    val userId: String = "",
    val username: String = "",
    val avatarUrl: String = "",
    val firstName: String = "",
    val lastName:String = "",
    val email: String = "",
    val bio: String = "",
    val dropDownMenuVisible: Boolean = false,

    val photoVisibility: String = "PUBLIC",
    val walkVisibility: String = "PUBLIC",

    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val friendsCount: Int = 0,

    val isFriends: Boolean = false,
    val isFollowedByUser:Boolean = false,

    val isLoading:Boolean = false,

    val photoUri:Uri = Uri.EMPTY,
    val status:Boolean = true
)