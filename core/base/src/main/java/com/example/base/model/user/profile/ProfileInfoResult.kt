package com.example.base.model.user.profile

import com.example.base.enums.PhotosVisibility
import com.example.base.enums.Role
import com.example.base.enums.WalkVisibility

class ProfileInfoResult(
    val userId: String,

    val username: String,
    val email: String,
    val firstname: String,
    val lastname: String,

    val bio: String,
    val role: Role, //enum
    val avatarUrl: String,

    val followersCount: Int,
    val followingCount: Int,
    val friendsCount: Int,

    val myPhotoVisibility: PhotosVisibility, //enum
    val savedPhotoVisibility:PhotosVisibility,
    val walkVisibility: WalkVisibility, //enum

) {
    companion object {
        val EMPTY = ProfileInfoResult(
            userId = "",
            username = "",
            email = "",
            firstname = "",
            lastname = "",
            bio = "",
            role = Role.ROLE_USER,
            avatarUrl = "",
            followersCount = 0,
            followingCount = 0,
            friendsCount = 0,
            myPhotoVisibility = PhotosVisibility.PUBLIC,
            savedPhotoVisibility = PhotosVisibility.PUBLIC,
            walkVisibility = WalkVisibility.PUBLIC,
        )
    }
}