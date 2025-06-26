package com.example.base.event.post

interface PostDetailEvent {

    data class DeleteComment(val message:String): PostDetailEvent

    data class NavigateToPersonProfile(val userId:String):PostDetailEvent
}