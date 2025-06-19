package com.example.domain.usecase

import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.DeletePostFromMySavedPostsUseCase

class DeletePostFromMySavedPostsUseCaseImpl(
    private val postRepository: PostRepository
): DeletePostFromMySavedPostsUseCase {
    override suspend fun invoke(postId: String): Result<Unit> {
        return postRepository.deletePostFromMySavedPosts(postId = postId)
    }
}