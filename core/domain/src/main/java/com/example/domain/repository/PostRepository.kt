package com.example.domain.repository

import com.example.base.model.post.Comment
import com.example.base.model.post.CommentCreateParam
import com.example.base.model.post.LikeResult
import com.example.base.model.post.PostCreateParam
import com.example.base.model.post.Post
import com.example.base.model.post.category.Category

interface PostRepository {
    suspend fun getRecommendedPosts(page: Int, limit: Int): Result<List<Post>>
    suspend fun deletePostFromMyPosts(postId: String): Result<Unit>
    suspend fun deletePostFromMySavedPosts(postId: String): Result<Unit>
    suspend fun getMyPosts(page: Int, limit: Int): Result<List<Post>>
    suspend fun getPostById(postId: String): Result<Post>
    suspend fun savePost(postId: String): Result<Unit>
    suspend fun getSavedPosts(page: Int, limit: Int): Result<List<Post>>
    suspend fun createPost(param: PostCreateParam): Result<Unit>
    suspend fun getPostsByUserId(userId: String, page: Int, limit: Int): Result<List<Post>>
    suspend fun getSavedPostsByUserId(userId: String, page: Int, limit: Int): Result<List<Post>>
    suspend fun getAllCommentsByPostId(postId: String, page: Int, limit: Int): Result<List<Comment>>
    suspend fun getAllCategories(): Result<List<Category>>
    suspend fun createComment(postId: String, text: CommentCreateParam): Result<Unit>
    suspend fun deleteComment(postId: String, commentId: String): Result<Unit>

    suspend fun likePost(postId: String):Result<LikeResult>
}