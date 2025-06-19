package com.example.domain.usecase

import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.DeletePostFromMyPostsUseCase

class DeletePostFromMyPostsUseCaseImpl(
    private val postRepository: PostRepository
): DeletePostFromMyPostsUseCase {
    override suspend fun invoke(postId: String): Result<Unit> {
       return postRepository.deletePostFromMyPosts(postId = postId)
    }
}