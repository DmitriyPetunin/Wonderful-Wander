package com.example.data.mapper

import com.example.domain.model.user.profile.PersonProfileInfoResult
import com.example.network.model.user.people.PersonProfileInfoResponse
import javax.inject.Inject

class PersonProfileInfoResponseToPersonProfileInfoResultMapper @Inject constructor(): (PersonProfileInfoResponse) -> com.example.domain.model.user.profile.PersonProfileInfoResult {
    override fun invoke(p1: PersonProfileInfoResponse): com.example.domain.model.user.profile.PersonProfileInfoResult {
        return p1.let {
            _root_ide_package_.com.example.domain.model.user.profile.PersonProfileInfoResult(
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