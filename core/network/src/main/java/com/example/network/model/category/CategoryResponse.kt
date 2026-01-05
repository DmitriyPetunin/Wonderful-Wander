package com.example.network.model.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse (
    val categoryId:Long,
    val name:String
)