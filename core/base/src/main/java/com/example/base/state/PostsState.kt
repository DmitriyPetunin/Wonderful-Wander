package com.example.base.state

import com.example.base.model.post.Post

data class PostsState (
    val isLoading:Boolean = true,

    val listOfPosts: List<Post> = emptyList(),
    val currentPagePosts: Int = 1,
    val limit:Int = 10,
    val isInitialLoadingPosts:Boolean = true,
    val endReachedPosts:Boolean = false,


    val postId: String = "", //пост для которого пишем коммент

    val showBottomSheet:Boolean = false,
    val commentText:String = ""
)