package com.example.data.mapper

import com.example.base.model.user.friends.Friend
import com.example.network.model.user.friends.FriendApi
import javax.inject.Inject

class FriendApiToFriendDomainMapper @Inject constructor(): (FriendApi?) -> Friend {
    override fun invoke(friendApi: FriendApi?): Friend {
        return friendApi?.let {
            Friend(
                userId = friendApi.userId.orEmpty(),
                avatarUrl = friendApi.avatarUrl.orEmpty(),
                username = friendApi.username.orEmpty()
            )
        }?: Friend.EMPTY
    }
}