package com.example.network.model.user.profile.res

import kotlinx.serialization.Serializable

@Serializable
data class DeleteProfileResponse (
    val timestamp: String?,
    val error:String?,
    val message:String?
)