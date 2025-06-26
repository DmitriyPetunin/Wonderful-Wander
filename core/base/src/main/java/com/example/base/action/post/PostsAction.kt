package com.example.base.action.post


sealed class PostsAction {
    data object SubmitCreatePost:PostsAction()
    data object LoadMorePosts:PostsAction()

    data object UpdateBottomSheetVisible:PostsAction()
    data object SuccessWritingCommentForPost:PostsAction()

    data class FastCommentClick(val postId:String):PostsAction()

    data class SubmitLikePost(val postId:String):PostsAction()
    data class UpdateCommentMessage(val message:String):PostsAction()
    data class SubmitSavePost(val postId:String):PostsAction()
    data class SubmitPostItem(val postId:String):PostsAction()
}