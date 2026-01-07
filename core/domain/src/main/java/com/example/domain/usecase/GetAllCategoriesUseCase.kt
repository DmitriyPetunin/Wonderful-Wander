package com.example.domain.usecase

import com.example.domain.model.post.category.Category


interface GetAllCategoriesUseCase {
    suspend fun invoke():Result<List<Category>>
}