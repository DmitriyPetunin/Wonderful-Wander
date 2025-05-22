package com.example.base.state

import com.example.base.enums.PhotosVisibility
import com.example.base.enums.WalkVisibility

data class ProfileState (
    val userId: String = "",
    val username: String = "",
    val avatarUrl: String = "",
    val firstName: String = "",
    val lastName:String = "",
    val email: String = "",
    val bio: String = "",

    val photoVisibility: String = "PUBLIC",
    val walkVisibility: String = "PUBLIC",

    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val friendsCount: Int = 0,
)