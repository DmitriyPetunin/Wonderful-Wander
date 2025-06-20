package com.example.base.event.post

import com.example.base.event.register.RegistrationEvent

sealed interface CreatePostEvent {
    data object SuccessCreatePost:CreatePostEvent
    data class ErrorCreatePost(val message: String):CreatePostEvent
}