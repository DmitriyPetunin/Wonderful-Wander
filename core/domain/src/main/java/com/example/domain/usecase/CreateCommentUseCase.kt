package com.example.domain.usecase

import com.example.domain.model.post.CommentCreateParam


interface CreateCommentUseCase {
    suspend fun invoke(postId:String,data: CommentCreateParam):Result<Unit>
}