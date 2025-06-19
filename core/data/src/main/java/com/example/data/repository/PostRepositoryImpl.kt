package com.example.data.repository

import com.example.base.model.post.PostResult
import com.example.base.model.post.UserDataResult
import com.example.base.model.post.category.Category
import com.example.data.mapper.PostResponseToPostDomainMapper
import com.example.domain.repository.PostRepository
import com.example.network.service.post.PostService
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val postDomainMapper: PostResponseToPostDomainMapper
) : PostRepository, BaseRepo() {


    override suspend fun savePost(postId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getPostById(postId: String): Result<PostResult> {
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

    override suspend fun getSavedPosts(page: Int, limit: Int): Result<List<PostResult>> {
//        return try {
//            val response = postService.getSavedPosts(page,limit)
//
//            when {
//                response.isSuccessful -> {
//                    Result.success(
//                        response.body()?.listOfPosts?.map {
//                            postDomainMapper.invoke(it)
//                        }?: emptyList()
//                    )
//                }
//                response.code() == 400 -> { Result.failure(Exception("ms")) }
//                response.code() == 401 -> { Result.failure(Exception("ms")) }
//                else -> { Result.failure(Exception("ms")) }
//            }
//        } catch (e:Exception){
//            e.printStackTrace()
//            Result.failure(Exception("ms"))
//        }

        val posts = mutableListOf<PostResult>()
        for (index in 0 until 10) {
            posts.add(
                PostResult(
                    postId = "post_$page + $index",
                    title = "",
                    photoUrl = "",
                    categoryName = "",
                    user = UserDataResult.EMPTY,
                    likesCount = 0,
                    commentsCount = 0,
                    createdAt = ""
                )
            )
        }

        posts.forEach(::println)

        return Result.success(posts)
    }

    override suspend fun getMyPosts(page: Int, limit: Int): Result<List<PostResult>> {
//        return try {
//            val response = postService.getPosts(page,limit)
//
//            when {
//                response.isSuccessful -> {
//                    Result.success(
//                        response.body()?.listOfPosts?.map {
//                            postDomainMapper.invoke(it)
//                        }?: emptyList()
//                    )
//                }
//                response.code() == 400 -> { Result.failure(Exception("ms")) }
//                response.code() == 401 -> { Result.failure(Exception("ms")) }
//                else -> { Result.failure(Exception("ms")) }
//            }
//        } catch (e:Exception){
//            e.printStackTrace()
//            Result.failure(Exception("ms"))
//        }

        val posts = mutableListOf<PostResult>()
        for (index in 0 until 10) {
            posts.add(
                PostResult(
                    postId = "post_$page + $index",
                    title = "",
                    photoUrl = "",
                    categoryName = "",
                    user = UserDataResult.EMPTY,
                    likesCount = 0,
                    commentsCount = 0,
                    createdAt = ""
                )
            )
        }

        posts.forEach(::println)

        return Result.success(posts)
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
    ): Result<List<PostResult>> {
//        return try {
//            val response = postService.getPostsByUserId(userId = userId,page = page,limit = limit)
//
//            when {
//                response.isSuccessful -> {
//                    Result.success(
//                        response.body()?.listOfPosts?.map {
//                            postDomainMapper.invoke(it)
//                        }?: emptyList()
//                    )
//                }
//                response.code() == 400 -> { Result.failure(Exception("ms")) }
//                response.code() == 401 -> { Result.failure(Exception("ms")) }
//                else -> { Result.failure(Exception("ms")) }
//            }
//        } catch (e:Exception){
//            e.printStackTrace()
//            Result.failure(Exception("ms"))
//        }
        val posts = mutableListOf<PostResult>()
        for (index in 0 until 10) {
            posts.add(
                PostResult(
                    postId = "post_$page + $index",
                    title = "",
                    photoUrl = "",
                    categoryName = "",
                    user = UserDataResult.EMPTY,
                    likesCount = 0,
                    commentsCount = 0,
                    createdAt = ""
                )
            )
        }

        posts.forEach(::println)

        return Result.success(posts)
    }

    override suspend fun getSavedPostsByUserId(
        userId: String,
        page: Int,
        limit: Int
    ): Result<List<PostResult>> {
//        return try {
//            val response = postService.getSavedPostsByUserId(userId = userId,page = page,limit = limit)
//
//            when {
//                response.isSuccessful -> {
//                    Result.success(
//                        response.body()?.listOfPosts?.map {
//                            postDomainMapper.invoke(it)
//                        }?: emptyList()
//                    )
//                }
//                response.code() == 400 -> { Result.failure(Exception("ms")) }
//                response.code() == 401 -> { Result.failure(Exception("ms")) }
//                else -> { Result.failure(Exception("ms")) }
//            }
//        } catch (e:Exception){
//            e.printStackTrace()
//            Result.failure(Exception("ms"))
//        }

        val posts = mutableListOf<PostResult>()
        for (index in 0 until 10) {
            posts.add(
                PostResult(
                    postId = "post_$page + $index",
                    title = "",
                    photoUrl = "",
                    categoryName = "",
                    user = UserDataResult.EMPTY,
                    likesCount = 0,
                    commentsCount = 0,
                    createdAt = ""
                )
            )
        }

        posts.forEach(::println)

        return Result.success(posts)
    }

    override suspend fun getAllCategories(): Result<List<Category>> {
//        return try {
//            val response = postService.getAllCategories()
//            when {
//                response.isSuccessful -> {
//                    Result.success(
//                        response.body()?.let { list ->
//                            list.map { Category(it.categoryId, it.name) }
//                        } ?: emptyList()
//                    )
//                }
//                else -> { Result.failure(Exception("")) }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Result.failure(e)
//        }

        val list = List(10) {index -> Category(categoryId = index.toLong(), name = "category$index")}

        return Result.success(list)
    }
}