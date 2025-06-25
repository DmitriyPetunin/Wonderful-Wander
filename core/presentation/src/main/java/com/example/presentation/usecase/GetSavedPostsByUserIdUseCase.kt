package com.example.presentation.usecase

import com.example.base.model.post.Post

interface GetSavedPostsByUserIdUseCase {
    suspend fun invoke(userId:String,page:Int = 0,limit:Int = 10):Result<List<Post>>
}