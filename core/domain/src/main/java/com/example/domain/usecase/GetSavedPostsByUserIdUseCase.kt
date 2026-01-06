package com.example.domain.usecase

import com.example.domain.model.post.Post


interface GetSavedPostsByUserIdUseCase {
    suspend fun invoke(userId:String,page:Int = 0,limit:Int = 10):Result<List<Post>>
}