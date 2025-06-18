package com.example.data.repository

import com.example.base.model.post.PostResult
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
                    }?: Result.failure(Exception(""))
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
        return Result.success(emptyList<PostResult>())
    }
}