package com.example.domain.repository

import com.example.base.model.post.PostResult

interface PostRepository {
    suspend fun getPostById(postId: String):Result<PostResult>
    suspend fun savePost(postId:String)
    suspend fun getSavedPosts(page: Int, limit: Int):Result<List<PostResult>>
}