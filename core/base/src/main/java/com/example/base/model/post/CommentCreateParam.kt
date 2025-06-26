package com.example.base.model.post

class CommentCreateParam(
    val text:String,
    val parentCommentId:String? = null
)