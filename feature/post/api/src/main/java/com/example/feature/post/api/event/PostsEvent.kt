package com.example.feature.post.api.event

sealed interface PostsEvent {
    data object NavigateToCreatePost:PostsEvent

    data class SavePost(val text:String):PostsEvent
    data class CreateComment(val text:String):PostsEvent
    data class NavigateToDetailPost(val postId:String):PostsEvent

}