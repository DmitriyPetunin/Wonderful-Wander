package com.example.base.action.post

sealed class PostDetailAction {


    data object NavigateBack : PostDetailAction()
    data object LikePost : PostDetailAction()
    data object CommentPost : PostDetailAction()
    data object ShowAllComments : PostDetailAction()

    data class UpdatePostId(val id:String):PostDetailAction()
    data class UserClicked(val userId: String) : PostDetailAction()


    data object LoadMoreComments:PostDetailAction()
}