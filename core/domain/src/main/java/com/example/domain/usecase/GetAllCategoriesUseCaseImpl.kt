package com.example.domain.usecase

import com.example.base.model.post.category.Category
import com.example.domain.repository.PostRepository
import com.example.presentation.usecase.GetAllCategoriesUseCase

class GetAllCategoriesUseCaseImpl(
    private val postRepository: PostRepository
): GetAllCategoriesUseCase {
    override suspend fun invoke(): Result<List<Category>> {
        return postRepository.getAllCategories()
    }
}