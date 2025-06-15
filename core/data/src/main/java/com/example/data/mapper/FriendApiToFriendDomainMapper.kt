package com.example.data.mapper

import com.example.base.model.user.People
import com.example.network.model.user.friends.FriendApi
import com.example.network.model.user.friends.PeopleApi
import javax.inject.Inject

class FriendApiToFriendDomainMapper @Inject constructor(): (PeopleApi?) -> People {
    override fun invoke(friendApi: PeopleApi?): People {
        return friendApi?.let {
            People(
                userId = friendApi.userId,
                avatarUrl = friendApi.avatarUrl ?: "",
                username = friendApi.userName
            )
        }?: People.EMPTY
    }
}