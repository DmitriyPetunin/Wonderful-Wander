package com.example.domain.usecase

import com.example.domain.model.post.Post


interface GetRecommendedPostsUseCase {
    suspend fun invoke(page:Int,limit:Int):Result<List<Post>>
}