package com.example.data.repository

import com.example.base.model.post.Comment
import com.example.base.model.post.PostCreateParam
import com.example.base.model.post.Post
import com.example.base.model.post.category.Category
import com.example.data.mapper.CommentResponseToCommentDomainMapper
import com.example.data.mapper.PostResponseToPostDomainMapper
import com.example.domain.repository.PostRepository
import com.example.network.model.post.req.UpdatePostRequest
import com.example.network.service.post.PostService
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val postDomainMapper: PostResponseToPostDomainMapper,
    private val commentDomainMapper: CommentResponseToCommentDomainMapper
) : PostRepository, BaseRepo() {

    override suspend fun getRecommendedPosts(): Result<List<Post>> {
        TODO("Not yet implemented")
    }

    override suspend fun savePost(postId: String): Result<Unit> {
        return try {
            val response = postService.savePost(postId)
            when{
                response.isSuccessful -> {Result.success(Unit)}
                else -> {Result.failure(Exception(""))}
            }
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getPostById(postId: String): Result<Post> {
        return try {
            val response = postService.getPostById(postId = postId)
            when {
                response.isSuccessful -> {
                    response.body()?.let {
                        Result.success(postDomainMapper.invoke(it))
                    } ?: Result.failure(Exception(""))
                }

                response.code() == 400 -> {
                    Result.failure(Exception(""))
                }

                response.code() == 401 -> {
                    Result.failure(Exception(""))
                }

                else -> {
                    Result.failure(Exception(""))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("ms"))
        }
    }

    override suspend fun getSavedPosts(page: Int, limit: Int): Result<List<Post>> {
        return try {
            val response = postService.getSavedPosts(page,limit)

            when {
                response.isSuccessful -> {
                    Result.success(
                        response.body()?.listOfPosts?.map {
                            postDomainMapper.invoke(it)
                        }?: emptyList()
                    )
                }
                response.code() == 400 -> { Result.failure(Exception("ms")) }
                response.code() == 401 -> { Result.failure(Exception("ms")) }
                else -> { Result.failure(Exception("ms")) }
            }
        } catch (e:Exception){
            e.printStackTrace()
            Result.failure(Exception("ms"))
        }
    }

    override suspend fun getMyPosts(page: Int, limit: Int): Result<List<Post>> {
        return try {
            val response = postService.getPosts(page,limit)

            when {
                response.isSuccessful -> {
                    Result.success(
                        response.body()?.listOfPosts?.map {
                            postDomainMapper.invoke(it)
                        }?: emptyList()
                    )
                }
                response.code() == 400 -> { Result.failure(Exception("ms")) }
                response.code() == 401 -> { Result.failure(Exception("ms")) }
                else -> { Result.failure(Exception("ms")) }
            }
        } catch (e:Exception){
            e.printStackTrace()
            Result.failure(Exception("ms"))
        }

    }

    override suspend fun deletePostFromMySavedPosts(postId: String): Result<Unit> {
        return try {
            val response = postService.deletePostFromMySavedPosts(postId = postId)
            when {
                response.isSuccessful -> {
                    Result.success(Unit)
                }

                response.code() == 400 -> {
                    Result.failure(Exception("ms"))
                }

                response.code() == 401 -> {
                    Result.failure(Exception("ms"))
                }

                response.code() == 403 -> {
                    Result.failure(Exception("ms"))
                }

                response.code() == 404 -> {
                    Result.failure(Exception("ms"))
                }

                else -> {
                    Result.failure(Exception("ms"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("ms"))
        }
    }

    override suspend fun deletePostFromMyPosts(postId: String): Result<Unit> {
        return try {
            val response = postService.deletePostById(postId = postId)
            when {
                response.isSuccessful -> {
                    Result.success(Unit)
                }

                response.code() == 400 -> {
                    Result.failure(Exception("ms"))
                }

                response.code() == 401 -> {
                    Result.failure(Exception("ms"))
                }

                response.code() == 403 -> {
                    Result.failure(Exception("ms"))
                }

                response.code() == 404 -> {
                    Result.failure(Exception("ms"))
                }

                else -> {
                    Result.failure(Exception("ms"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(Exception("ms"))
        }
    }

    override suspend fun getPostsByUserId(
        userId: String,
        page: Int,
        limit: Int
    ): Result<List<Post>> {
        return try {
            val response = postService.getPostsByUserId(userId = userId,page = page,limit = limit)

            when {
                response.isSuccessful -> {
                    Result.success(
                        response.body()?.listOfPosts?.map {
                            postDomainMapper.invoke(it)
                        }?: emptyList()
                    )
                }
                response.code() == 400 -> { Result.failure(Exception("ms")) }
                response.code() == 401 -> { Result.failure(Exception("ms")) }
                else -> { Result.failure(Exception("ms")) }
            }
        } catch (e:Exception){
            e.printStackTrace()
            Result.failure(Exception("ms"))
        }

    }

    override suspend fun getSavedPostsByUserId(
        userId: String,
        page: Int,
        limit: Int
    ): Result<List<Post>> {
        return try {
            val response = postService.getSavedPostsByUserId(userId = userId,page = page,limit = limit)

            when {
                response.isSuccessful -> {
                    Result.success(
                        response.body()?.listOfPosts?.map {
                            postDomainMapper.invoke(it)
                        }?: emptyList()
                    )
                }
                response.code() == 400 -> { Result.failure(Exception("ms")) }
                response.code() == 401 -> { Result.failure(Exception("ms")) }
                else -> { Result.failure(Exception("ms")) }
            }
        } catch (e:Exception){
            e.printStackTrace()
            Result.failure(Exception("ms"))
        }
    }

    override suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val response = postService.getAllCategories()
            when {
                response.isSuccessful -> {
                    Result.success(
                        response.body()?.let { list ->
                            list.map { Category(it.categoryId, it.name) }
                        } ?: emptyList()
                    )
                }
                else -> { Result.failure(Exception("")) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun createPost(param: PostCreateParam): Result<Unit> {
        return try {
            val response = postService.createPost(
                with(param) {
                    UpdatePostRequest(
                        title = title,
                        categoryId = categoryId,
                        imageFilename = imageFilename
                    )
                }
            )
            when {
                response.isSuccessful -> {
                    Result.success(Unit)
                }

                response.code() == 400 -> {
                    Result.failure(Exception(""))
                }

                response.code() == 401 -> {
                    Result.failure(Exception(""))
                }

                else -> { Result.failure(Exception("")) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getAllCommentsByPostId(
        postId: String,
        page: Int,
        limit: Int
    ): Result<List<Comment>> {
        return try {
            val response = postService.getAllCommentsByPostId(postId,page,limit)
            when{
                response.isSuccessful -> {
                    Result.success(response.body()?.listOfComments?.map {
                        commentDomainMapper.invoke(it)
                    } ?: emptyList())
            }
                response.code() == 400 -> {Result.failure(Exception(""))}
                response.code() == 401 -> {Result.failure(Exception(""))}
                response.code() == 404 -> {Result.failure(Exception(""))}
                else -> {Result.failure(Exception(""))}
            }
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }
}