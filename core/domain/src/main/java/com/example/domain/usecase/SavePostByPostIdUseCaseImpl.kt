package com.example.domain.usecase

import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.SavePostByPostIdUseCase

class SavePostByPostIdUseCaseImpl(
    private val postRepository: PostRepository
): SavePostByPostIdUseCase {
    override suspend fun invoke(postId: String): Result<Unit> {
        return postRepository.savePost(postId)
    }
}