package com.example.feature.post.api.event

sealed interface CreatePostEvent {
    data object SuccessCreatePost:CreatePostEvent
    data class ErrorCreatePost(val message: String):CreatePostEvent
}