package com.example.domain.usecase

import com.example.base.model.post.PostResult
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.GetMyPostsUseCase

class GetMyPostsUseCaseImpl(
    private val postRepository: PostRepository
): GetMyPostsUseCase {
    override suspend fun invoke(page:Int,limit:Int): Result<List<PostResult>> {
        return postRepository.getMyPosts(page,limit)
    }
}