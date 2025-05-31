package com.example.network.model.user.people

import kotlinx.serialization.Serializable

@Serializable
data class GetPersonProfileInfo(
    val userId:String?,
    val userName:String?,
    val avatarUrl:String?
)