package com.example.domain.usecaseimpl

import com.example.base.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.domain.usecase.GetPostDetailByIdUseCase

class GetPostDetailByIdUseCaseImpl(
    private val postRepository: PostRepository
): GetPostDetailByIdUseCase {
    override suspend fun invoke(postId: String): Result<Post> {
        return postRepository.getPostById(postId = postId)
    }
}