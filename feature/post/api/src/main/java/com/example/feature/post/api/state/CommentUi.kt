package com.example.feature.post.api.state

import com.example.base.model.post.Comment

data class CommentUi(
    val comment: Comment,
    val canDelete:Boolean
)