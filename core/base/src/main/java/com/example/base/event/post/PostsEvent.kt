package com.example.base.event.post

sealed interface PostsEvent {
    data object NavigateToCreatePost:PostsEvent

    data class SavePost(val text:String):PostsEvent
    data class CreateComment(val text:String):PostsEvent
    data class NavigateToDetailPost(val postId:String):PostsEvent

}