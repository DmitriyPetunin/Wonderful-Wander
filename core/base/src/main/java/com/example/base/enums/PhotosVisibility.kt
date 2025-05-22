package com.example.base.enums

enum class PhotosVisibility {
    PUBLIC,FRIENDS_ONLY,PRIVATE,UNKNOWN;


    companion object {
        fun fromString(visibility: String?): PhotosVisibility {
            return when (visibility) {
                "PUBLIC" -> PUBLIC
                "PRIVATE" -> PRIVATE
                "FRIENDS_ONLY" -> FRIENDS_ONLY
                else -> UNKNOWN
            }
        }
        fun toStringValue(visibility: PhotosVisibility): String {
            return when (visibility) {
                PUBLIC -> "PUBLIC"
                FRIENDS_ONLY -> "FRIENDS_ONLY"
                PRIVATE -> "PRIVATE"
                else -> "UNKNOWN"
            }
        }
    }
}
