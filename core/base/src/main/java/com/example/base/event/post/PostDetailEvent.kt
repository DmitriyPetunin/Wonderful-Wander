package com.example.base.event.post

interface PostDetailEvent {

    data class NavigateToUserProfile(val userId: String): PostDetailEvent

    data class DeleteComment(val message:String): PostDetailEvent
}