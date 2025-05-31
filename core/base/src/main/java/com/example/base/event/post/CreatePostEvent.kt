package com.example.base.event.post

sealed interface CreatePostEvent {
    data object NavigateToUploadImageScreen: CreatePostEvent
}