package com.example.domain.usecaseimpl

import com.example.base.model.post.PostCreateParam
import com.example.domain.repository.PostRepository
import com.example.domain.usecase.CreatePostUseCase

class CreatePostUseCaseImpl(
    private val postRepository: PostRepository
): CreatePostUseCase {
    override suspend fun invoke(postParam: PostCreateParam): Result<Unit> {
        return postRepository.createPost(postParam)
    }
}