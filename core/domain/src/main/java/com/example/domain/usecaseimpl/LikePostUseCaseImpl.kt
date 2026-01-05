package com.example.domain.usecaseimpl

import com.example.base.model.post.LikeResult
import com.example.domain.repository.PostRepository
import com.example.domain.usecase.LikePostUseCase

class LikePostUseCaseImpl(
    private val postRepository: PostRepository
): LikePostUseCase {
    override suspend fun invoke(postId: String): Result<LikeResult> {
        return postRepository.likePost(postId)
    }
}