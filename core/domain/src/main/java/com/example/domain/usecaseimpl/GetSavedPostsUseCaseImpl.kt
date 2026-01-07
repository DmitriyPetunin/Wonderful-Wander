package com.example.domain.usecaseimpl

import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.domain.usecase.GetSavedPostsUseCase

class GetSavedPostsUseCaseImpl(
    private val postRepository: PostRepository
): GetSavedPostsUseCase {
    override suspend fun invoke(page: Int, limit: Int): Result<List<Post>> {
        return postRepository.getSavedPosts(page, limit)
    }
}