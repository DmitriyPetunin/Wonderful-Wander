package com.example.data.mapper

import com.example.base.model.user.People
import com.example.network.model.user.friends.FriendApi
import javax.inject.Inject

class FriendApiToFriendDomainMapper @Inject constructor(): (FriendApi?) -> People {
    override fun invoke(friendApi: FriendApi?): People {
        return friendApi?.let {
            People(
                userId = friendApi.userId.orEmpty(),
                avatarUrl = friendApi.avatarUrl.orEmpty(),
                username = friendApi.userName.orEmpty()
            )
        }?: People.EMPTY
    }
}