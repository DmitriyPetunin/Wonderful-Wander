package com.example.domain.usecaseimpl

import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.domain.usecase.GetPostsByUserIdUseCase

class GetPostsByUserIdUseCaseImpl(
    private val postRepository: PostRepository
): GetPostsByUserIdUseCase {
    override suspend fun invoke(userId: String, page:Int, limit:Int): Result<List<Post>> {
        return postRepository.getPostsByUserId(userId = userId,page = page,limit = limit)
    }
}