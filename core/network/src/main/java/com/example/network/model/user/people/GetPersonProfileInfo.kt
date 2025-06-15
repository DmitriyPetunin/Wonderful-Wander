package com.example.network.model.user.people

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetPersonProfileInfoResponse(
    val userId:String,
    @SerialName("username")
    val userName:String,
    val avatarUrl:String?

)