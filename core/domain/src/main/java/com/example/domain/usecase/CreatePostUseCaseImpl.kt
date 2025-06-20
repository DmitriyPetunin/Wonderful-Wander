package com.example.domain.usecase

import com.example.base.model.post.PostCreateParam
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.CreatePostUseCase

class CreatePostUseCaseImpl(
    private val postRepository: PostRepository
): CreatePostUseCase {
    override suspend fun invoke(postParam: PostCreateParam): Result<Unit> {
        return postRepository.createPost(postParam)
    }
}