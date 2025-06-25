package com.example.base.state

import com.example.base.model.post.Comment
import com.example.base.model.post.UserDataResult

data class CommentUi(
    val comment: Comment,
    val canDelete:Boolean
)