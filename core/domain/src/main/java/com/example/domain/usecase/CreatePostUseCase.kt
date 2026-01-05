package com.example.domain.usecase

import com.example.base.model.post.PostCreateParam

interface CreatePostUseCase {
    suspend fun invoke(postParam: PostCreateParam):Result<Unit>
}