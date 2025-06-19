package com.example.domain.repository

import com.example.base.model.post.PostResult

interface PostRepository {
    suspend fun deletePostFromMyPosts(postId:String):Result<Unit>
    suspend fun deletePostFromMySavedPosts(postId:String):Result<Unit>
    suspend fun getMyPosts(page: Int,limit: Int):Result<List<PostResult>>
    suspend fun getPostById(postId: String):Result<PostResult>
    suspend fun savePost(postId:String)
    suspend fun getSavedPosts(page: Int, limit: Int):Result<List<PostResult>>

    suspend fun getPostsByUserId(userId:String,page: Int, limit: Int):Result<List<PostResult>>
    suspend fun getSavedPostsByUserId(userId:String,page: Int, limit: Int):Result<List<PostResult>>
}