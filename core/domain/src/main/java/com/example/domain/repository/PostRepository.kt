package com.example.domain.repository

import com.example.base.model.post.PostCreateParam
import com.example.base.model.post.PostResult
import com.example.base.model.post.category.Category

interface PostRepository {
    suspend fun getRecommendedPosts():Result<List<PostResult>>
    suspend fun deletePostFromMyPosts(postId:String):Result<Unit>
    suspend fun deletePostFromMySavedPosts(postId:String):Result<Unit>
    suspend fun getMyPosts(page: Int,limit: Int):Result<List<PostResult>>
    suspend fun getPostById(postId: String):Result<PostResult>
    suspend fun savePost(postId:String):Result<Unit>
    suspend fun getSavedPosts(page: Int, limit: Int):Result<List<PostResult>>
    suspend fun createPost(param:PostCreateParam):Result<Unit>
    suspend fun getPostsByUserId(userId:String,page: Int, limit: Int):Result<List<PostResult>>
    suspend fun getSavedPostsByUserId(userId:String,page: Int, limit: Int):Result<List<PostResult>>

    suspend fun getAllCategories():Result<List<Category>>
}