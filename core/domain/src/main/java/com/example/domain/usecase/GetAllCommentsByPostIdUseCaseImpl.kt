package com.example.domain.usecase

import com.example.base.model.post.Comment
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.GetAllCommentsByPostIdUseCase

class GetAllCommentsByPostIdUseCaseImpl(
    private val postRepository: PostRepository
): GetAllCommentsByPostIdUseCase {
    override suspend fun invoke(postId: String, page: Int, limit: Int): Result<List<Comment>> {
        return postRepository.getAllCommentsByPostId(postId,page,limit)
    }
}