package com.example.domain.usecase

import com.example.base.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.GetPostsByUserIdUseCase

class GetPostsByUserIdUseCaseImpl(
    private val postRepository: PostRepository
): GetPostsByUserIdUseCase {
    override suspend fun invoke(userId: String, page:Int, limit:Int): Result<List<Post>> {
        return postRepository.getPostsByUserId(userId = userId,page = page,limit = limit)
    }
}