package com.example.domain.usecaseimpl

import com.example.domain.model.post.Post
import com.example.domain.repository.PostRepository
import com.example.domain.usecase.GetMyPostsUseCase

class GetMyPostsUseCaseImpl(
    private val postRepository: PostRepository
): GetMyPostsUseCase {
    override suspend fun invoke(page:Int,limit:Int): Result<List<Post>> {
        return postRepository.getMyPosts(page,limit)
    }
}