package com.example.domain.usecase

import com.example.base.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.GetSavedPostsUseCase

class GetSavedPostsUseCaseImpl(
    private val postRepository: PostRepository
): GetSavedPostsUseCase {
    override suspend fun invoke(page: Int, limit: Int): Result<List<Post>> {
        return postRepository.getSavedPosts(page, limit)
    }
}