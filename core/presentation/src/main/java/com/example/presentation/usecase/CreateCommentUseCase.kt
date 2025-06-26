package com.example.presentation.usecase

import com.example.base.model.post.CommentCreateParam

interface CreateCommentUseCase {
    suspend fun invoke(postId:String,data: CommentCreateParam):Result<Unit>
}