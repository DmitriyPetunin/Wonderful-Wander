package com.example.domain.usecaseimpl

import com.example.base.model.post.CommentCreateParam
import com.example.domain.repository.PostRepository
import com.example.domain.usecase.CreateCommentUseCase

class CreateCommentUseCaseImpl(
    private val postRepository: PostRepository
): CreateCommentUseCase {
    override suspend fun invoke(postId: String, data: CommentCreateParam): Result<Unit> {
        return postRepository.createComment(postId,data)
    }
}