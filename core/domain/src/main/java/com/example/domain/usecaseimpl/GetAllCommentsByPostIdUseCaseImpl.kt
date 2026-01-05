package com.example.domain.usecaseimpl

import com.example.base.model.post.Comment
import com.example.domain.repository.PostRepository
import com.example.domain.usecase.GetAllCommentsByPostIdUseCase

class GetAllCommentsByPostIdUseCaseImpl(
    private val postRepository: PostRepository
): GetAllCommentsByPostIdUseCase {
    override suspend fun invoke(postId: String, page: Int, limit: Int): Result<List<Comment>> {
        return postRepository.getAllCommentsByPostId(postId,page,limit)
    }
}