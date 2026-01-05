package com.example.domain.model.post

class CommentCreateParam(
    val text:String,
    val parentCommentId:String? = null
)