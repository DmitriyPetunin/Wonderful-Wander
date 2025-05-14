package com.example.network.model.user.profile.res

import com.example.network.model.error.Violation
import kotlinx.serialization.Serializable


@Serializable
data class UpdateProfileResponse(
    val userId:String?,

    val username:String?,
    val email:String?,
    val firstname:String?,
    val lastname:String?,

    val bio:String?,
    val role:String?, //enum
    val avatarUrl:String?,

    val followersCount:Int?,
    val followingCount:Int?,
    val friendsCount:Int?,

    val photoVisibility:String?, //enum
    val walkVisibility:String?, //enum

    val timestamp: String?,
    val error:String?,
    val violations:List<Violation>?,


    val statusCode:String?
)