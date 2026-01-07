package com.example.data.mapper

import com.example.domain.model.user.People
import com.example.network.model.user.friends.FriendApi
import com.example.network.model.user.friends.PeopleApi
import javax.inject.Inject

class FriendApiToFriendDomainMapper @Inject constructor(): (PeopleApi?) -> com.example.domain.model.user.People {
    override fun invoke(friendApi: PeopleApi?): com.example.domain.model.user.People {
        return friendApi?.let {
            _root_ide_package_.com.example.domain.model.user.People(
                userId = friendApi.userId,
                avatarUrl = friendApi.avatarUrl ?: "",
                username = friendApi.userName
            )
        }?: _root_ide_package_.com.example.domain.model.user.People.EMPTY
    }
}