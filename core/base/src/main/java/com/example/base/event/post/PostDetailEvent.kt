package com.example.base.event.post

interface PostDetailEvent {

    data class NavigateToUserProfile(val userId: String): PostDetailEvent
}