package com.example.feature.post.api.event

interface PostDetailEvent {

    data class DeleteComment(val message:String): PostDetailEvent

    data class NavigateToPersonProfile(val userId:String):PostDetailEvent
}