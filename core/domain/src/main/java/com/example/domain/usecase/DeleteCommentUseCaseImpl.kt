package com.example.domain.usecase

import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.DeleteCommentUseCase

class DeleteCommentUseCaseImpl(
    private val postRepository: PostRepository
) : DeleteCommentUseCase {
    override suspend fun invoke(postId: String, commentId: String): Result<Unit> {
        return postRepository.deleteComment(postId = postId, commentId = commentId)
    }
}