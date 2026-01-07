package com.example.domain.model.post

data class PostCreateParam (
    val title:String,
    val imageFilename:String,
    val categoryId:Long,
)