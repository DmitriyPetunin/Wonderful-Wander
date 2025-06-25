package com.example.domain.usecase

import com.example.base.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.GetSavedPostsByUserIdUseCase

class GetSavedPostsByUserIdUseCaseImpl(
    private val postRepository: PostRepository
): GetSavedPostsByUserIdUseCase {
    override suspend fun invoke(userId: String, page: Int, limit: Int): Result<List<Post>> {
        return postRepository.getSavedPostsByUserId(userId = userId,page = page,limit = limit)
    }
}