package com.example.domain.usecase

import com.example.domain.model.post.Post


interface GetMyPostsUseCase {
    suspend fun invoke(page:Int = 0,limit:Int = 10):Result<List<Post>>
}