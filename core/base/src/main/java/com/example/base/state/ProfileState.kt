package com.example.base.state

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

    val followersCount: Int = 391,
    val followingCount: Int = 120,
    val friendsCount: Int = 71,

    val isFollowing: Boolean = false
)