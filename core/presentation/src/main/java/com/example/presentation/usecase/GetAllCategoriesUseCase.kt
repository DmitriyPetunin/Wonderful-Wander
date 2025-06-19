package com.example.presentation.usecase

import com.example.base.model.post.category.Category

interface GetAllCategoriesUseCase {
    suspend fun invoke():Result<List<Category>>
}