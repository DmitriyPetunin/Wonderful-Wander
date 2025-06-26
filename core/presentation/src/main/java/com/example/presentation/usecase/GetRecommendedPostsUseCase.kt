package com.example.presentation.usecase

import com.example.base.model.post.Post

interface GetRecommendedPostsUseCase {
    suspend fun invoke(page:Int,limit:Int):Result<List<Post>>
}