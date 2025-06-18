package com.example.base.state

import android.net.Uri
import com.example.base.model.post.PostResult

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

    val isLoading:Boolean = true,
    val endReached:Boolean = false,

    val photoUri:Uri = Uri.EMPTY,
    val status:Boolean = true,
    val selectedTabIndex: Int = 0,

    val listOfSavedPostResults: List<PostResult> = emptyList(),
    val listOfMyPosts: List<PostResult> = emptyList(),
    val currentPage: Int = 1,
    val limit:Int = 10,



    )