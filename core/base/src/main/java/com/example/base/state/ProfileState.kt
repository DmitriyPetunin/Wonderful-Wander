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

    val myPhotoVisibility: String = "PUBLIC",
    val savedPhotoVisibility:String = "PUBLIC",
    val walkVisibility: String = "PUBLIC",

    val followersCount: Int = 0,
    val followingCount: Int = 0,
    val friendsCount: Int = 0,

    val isFriends: Boolean = false,
    val isFollowedByUser:Boolean = false,

    val isLoading:Boolean = true,

    val photoUri:Uri = Uri.EMPTY,
    val status:Boolean = true,
    val selectedTabIndex: Int = 0,

    val listOfSavedPosts: List<PostResult> = emptyList(),
    val currentPageSavedPosts: Int = 1,
    val isInitialLoadingSavedPosts:Boolean = true,
    val endReachedSavedPosts:Boolean = false,


    val listOfMyPosts: List<PostResult> = emptyList(),
    val currentPageMyPosts: Int = 1,
    val isInitialLoadingMyPosts:Boolean = true,
    val endReachedMyPosts:Boolean = false,

    val limit:Int = 10,

    val isItMyProfile:Boolean = true

    )