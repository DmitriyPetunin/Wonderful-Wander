package com.example.presentation.usecase

import com.example.base.model.post.PostResult

interface GetSavedPostsUseCase {
    suspend fun invoke(page:Int = 0,limit:Int = 10):Result<List<PostResult>>
}