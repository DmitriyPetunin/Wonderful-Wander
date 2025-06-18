package com.example.domain.usecase

import com.example.base.model.post.PostResult
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.GetPostDetailByIdUseCase

class GetPostDetailByIdUseCaseImpl(
    private val postRepository: PostRepository
): GetPostDetailByIdUseCase {
    override suspend fun invoke(postId: String): Result<PostResult> {
        return postRepository.getPostById(postId = postId)
    }
}