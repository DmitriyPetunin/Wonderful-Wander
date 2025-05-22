package com.example.base.enums

enum class WalkVisibility {
    PUBLIC,FRIENDS_ONLY,REMEMBER_ONLY,PRIVATE,UNKNOWN;

    companion object {
        fun fromString(visibility: String?): WalkVisibility {
            return when (visibility) {
                "PUBLIC" -> PUBLIC
                "FRIENDS_ONLY" -> FRIENDS_ONLY
                "REMEMBER_ONLY" -> REMEMBER_ONLY
                "PRIVATE" -> PRIVATE
                else -> UNKNOWN
            }
        }
        fun toStringValue(visibility: WalkVisibility): String {
            return when (visibility) {
                PUBLIC -> "PUBLIC"
                FRIENDS_ONLY -> "FRIENDS_ONLY"
                REMEMBER_ONLY -> "REMEMBER_ONLY"
                PRIVATE -> "PRIVATE"
                else -> "UNKNOWN"
            }
        }
    }
}