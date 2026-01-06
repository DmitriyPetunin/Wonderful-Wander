package com.example.domain.usecase

import com.example.domain.model.post.PostCreateParam


interface CreatePostUseCase {
    suspend fun invoke(postParam: PostCreateParam):Result<Unit>
}