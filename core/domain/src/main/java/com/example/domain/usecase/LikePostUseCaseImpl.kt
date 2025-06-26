package com.example.domain.usecase

import com.example.base.model.post.LikeResult
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.LikePostUseCase

class LikePostUseCaseImpl(
    private val postRepository: PostRepository
): LikePostUseCase {
    override suspend fun invoke(postId: String): Result<LikeResult> {
        return postRepository.likePost(postId)
    }
}