package com.example.network.service.post

import com.example.network.model.photo.UploadImageResponse
import com.example.network.model.post.req.CreateCommentRequest
import com.example.network.model.post.req.UpdatePostRequest
import com.example.network.model.post.res.CategoryResponse
import com.example.network.model.post.res.CommentResponse
import com.example.network.model.post.res.CommentsResponse
import com.example.network.model.post.res.LikeResponse
import com.example.network.model.post.res.PostResponse
import com.example.network.model.post.res.PostsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {


    @GET("/api/posts/recommendations")
    suspend fun getRecommendedPosts(
        @Query("page") page: Int,
        @Query("size") limit: Int
    ): Response<PostsResponse>

    @POST("/api/posts")
    suspend fun createPost(
        @Body body: UpdatePostRequest
    ): Response<PostResponse>

    @POST("/api/posts/saved/{postId}")
    suspend fun savePost(
        @Path("postId") postId: String,
    ): Response<PostResponse>

    @DELETE("/api/posts/saved/{postId}")
    suspend fun deletePostFromMySavedPosts(
        @Path("postId") postId: String,
    ): Response<Unit>


    @GET("/api/posts/users/{userId}/saved")
    suspend fun getSavedPostsByUserId(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("size") limit: Int
    ): Response<PostsResponse>

    @GET("/api/posts/users/{userId}/posts")
    suspend fun getPostsByUserId(
        @Path("userId") userId: String,
        @Query("page") page: Int,
        @Query("size") limit: Int
    ): Response<PostsResponse>


    @GET("/api/posts/saved")
    suspend fun getSavedPosts(
        @Query("page") page: Int,
        @Query("size") limit: Int
    ): Response<PostsResponse>

    @GET("/api/posts/me")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("size") limit: Int
    ): Response<PostsResponse>

    @GET("/api/posts/{postId}")
    suspend fun getPostById(
        @Path("postId") postId: String
    ): Response<PostResponse>

    @PUT("/api/posts/{postId}")
    suspend fun updatePostById(
        @Path("postId") postId: String,
        @Body body: UpdatePostRequest
    ): Response<PostResponse>

    @DELETE("/api/posts/{postId}")
    suspend fun deletePostById(
        @Path("postId") postId: String,
    ): Response<Unit>

    @GET("/api/posts/category")
    suspend fun getAllCategories(
    ): Response<List<CategoryResponse>>


    @GET("/api/posts/{postId}/comments")
    suspend fun getAllCommentsByPostId(
        @Path("postId") postId: String,
        @Query("page") page: Int,
        @Query("size") limit: Int
    ): Response<CommentsResponse>


    @DELETE("/api/posts/{postId}/comments/{commentId}")
    suspend fun deleteComment(
        @Path("postId") postId: String,
        @Path("commentId") commentId: String
    ): Response<Unit>


    @POST("/api/posts/{postId}/comments")
    suspend fun createComment(
        @Path("postId") postId: String,
        @Body data: CreateCommentRequest
    ):Response<Unit>

    @POST("/api/posts/{postId}/likes")
    suspend fun likePost(
        @Path("postId") postId: String,
    ):Response<LikeResponse>
}