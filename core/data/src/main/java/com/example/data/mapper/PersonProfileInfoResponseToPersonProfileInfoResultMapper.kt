package com.example.data.mapper

import com.example.base.model.user.profile.PersonProfileInfoResult
import com.example.network.model.user.people.PersonProfileInfoResponse
import javax.inject.Inject

class PersonProfileInfoResponseToPersonProfileInfoResultMapper @Inject constructor(): (PersonProfileInfoResponse) -> PersonProfileInfoResult {
    override fun invoke(p1: PersonProfileInfoResponse): PersonProfileInfoResult {
        return p1.let {
            PersonProfileInfoResult(
                userId = it.userId,
                userName = it.userName,
                firstname = it.lastname,
                lastname = it.lastname,
                bio = it.bio,
                avatarUrl = it.avatarUrl ?: "",
                followersCount = it.followersCount,
                followingCount = it.followingCount,
                friendsCount = it.friendsCount,
                isFollowedByUser = it.isFollowedByUser,
                isFollowingByUser = it.isFollowingByUser,
                isFriends = it.isFriends
            )
        }
    }
}