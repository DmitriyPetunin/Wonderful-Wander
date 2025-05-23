package com.example.data.mapper

import com.example.base.enums.PhotosVisibility
import com.example.base.enums.Role
import com.example.base.enums.WalkVisibility
import com.example.base.model.user.profile.ProfileInfoResult
import com.example.network.model.user.profile.res.GetProfileResponse
import javax.inject.Inject

class ProfileResponseToProfileInfoMapper @Inject constructor() : (GetProfileResponse?) -> ProfileInfoResult {
    override fun invoke(profileResponse: GetProfileResponse?): ProfileInfoResult {
        return profileResponse?.let {
            ProfileInfoResult(
                userId = it.userId.orEmpty(),
                username = it.username.orEmpty(),
                email = it.email.orEmpty(),
                firstname = it.firstname.orEmpty(),
                lastname = it.lastname.orEmpty(),
                bio = it.bio.orEmpty(),
                role = Role.fromString(it.role),
                avatarUrl = it.avatarUrl.orEmpty(),
                followersCount = it.followersCount ?: 0,
                followingCount = it.followingCount ?: 0,
                friendsCount = it.friendsCount ?: 0,
                photoVisibility = PhotosVisibility.fromString(it.photoVisibility),
                walkVisibility = WalkVisibility.fromString(it.walkVisibility)
            )
        } ?: ProfileInfoResult.EMPTY
    }
}