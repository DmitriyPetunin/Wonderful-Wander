package com.example.domain.usecase

interface DeleteCommentUseCase {
    suspend fun invoke(postId:String,commentId:String):Result<Unit>
}