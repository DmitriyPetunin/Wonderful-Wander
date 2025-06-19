package com.example.network.model.category

import com.example.base.model.post.category.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse (
    val categoryId:Long,
    val name:String
)