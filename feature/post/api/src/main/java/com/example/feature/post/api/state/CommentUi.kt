package com.example.feature.post.api.state

import com.example.domain.model.post.Comment


data class CommentUi(
    val comment: Comment,
    val canDelete:Boolean
)