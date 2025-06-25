package com.example.data.mapper

import com.example.base.model.post.Comment
import com.example.base.model.post.UserDataResult
import com.example.network.model.post.res.CommentResponse
import javax.inject.Inject

class CommentResponseToCommentDomainMapper @Inject constructor(): (CommentResponse?) -> Comment {
    override fun invoke(p1: CommentResponse?): Comment {
        return p1?.let {
            Comment(
                commentId = it.commentId,
                text = it.text,
                user = UserDataResult(userId = it.user.userId, avatarUrl = it.user.avatarUrl ?: "", userName = it.user.userName),
                createdAt = it.createdAt,
                repliesCount = it.repliesCount,
            )
        }  ?: Comment.EMPTY
    }
}