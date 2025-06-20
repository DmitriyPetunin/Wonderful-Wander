package com.example.presentation.usecase

import com.example.base.model.post.PostResult

interface GetRecommendedPostsUseCase {
    suspend fun invoke():Result<List<PostResult>>
}