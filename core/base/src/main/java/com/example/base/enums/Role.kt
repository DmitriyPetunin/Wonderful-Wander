package com.example.base.enums

import com.example.base.enums.PhotosVisibility.FRIENDS_ONLY
import com.example.base.enums.PhotosVisibility.PRIVATE
import com.example.base.enums.PhotosVisibility.PUBLIC
import com.example.base.enums.PhotosVisibility.UNKNOWN

enum class Role {
    ROLE_USER,ROLE_ADMIN,UNKNOWN;

    fun toStringValue(): String {
        return when (this) {
            ROLE_USER -> "PUBLIC"
            ROLE_ADMIN -> "FRIENDS_ONLY"
            UNKNOWN -> "PRIVATE"
        }
    }

    companion object {
        fun fromString(visibility: String?): Role {
            return when (visibility) {
                "ROLE_USER" -> ROLE_USER
                "ROLE_ADMIN" -> ROLE_ADMIN
                else -> UNKNOWN
            }
        }
    }
}