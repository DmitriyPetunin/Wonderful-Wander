package com.example.data.mapper

import com.example.domain.model.post.Comment
import com.example.domain.model.post.UserDataResult
import com.example.network.model.post.res.CommentResponse
import javax.inject.Inject

class CommentResponseToCommentDomainMapper @Inject constructor(): (CommentResponse?) -> com.example.domain.model.post.Comment {
    override fun invoke(p1: CommentResponse?): com.example.domain.model.post.Comment {
        return p1?.let {
            _root_ide_package_.com.example.domain.model.post.Comment(
                commentId = it.commentId,
                text = it.text,
                user = _root_ide_package_.com.example.domain.model.post.UserDataResult(
                    userId = it.user.userId,
                    avatarUrl = it.user.avatarUrl ?: "",
                    userName = it.user.userName
                ),
                createdAt = it.createdAt,
                repliesCount = it.repliesCount,
            )
        }  ?: _root_ide_package_.com.example.domain.model.post.Comment.EMPTY
    }
}