package com.example.domain.usecase

import com.example.base.model.post.PostResult
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.GetRecommendedPostsUseCase

class GetRecommendedPostsUseCaseImpl(
    private val postRepository: PostRepository
): GetRecommendedPostsUseCase {
    override suspend fun invoke(): Result<List<PostResult>> {
        return postRepository.getRecommendedPosts()
    }
}