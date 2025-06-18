package com.example.data.mapper

import com.example.base.model.post.PostResult
import com.example.base.model.post.UserDataResult
import com.example.network.model.post.res.PostResponse
import javax.inject.Inject

class PostResponseToPostDomainMapper @Inject constructor(): (PostResponse?) -> PostResult{
    override fun invoke(p1: PostResponse?): PostResult {
        return p1?.let {
            PostResult(
                title = it.title,
                postId = it.postId,
                photoUrl = it.photoUrl,
                likesCount = it.likesCount,
                commentsCount = it.commentsCount,
                createdAt = it.createdAt,
                categoryName = it.category.name,
                user = UserDataResult(userId = it.user.userId, avatarUrl = it.user.avatarUrl, userName = it.user.userName)
            )
        }?:PostResult.EMPTY
    }

}