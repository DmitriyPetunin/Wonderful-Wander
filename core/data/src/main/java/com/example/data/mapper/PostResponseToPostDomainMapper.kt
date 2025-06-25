package com.example.data.mapper

import com.example.base.model.post.Post
import com.example.base.model.post.UserDataResult
import com.example.network.model.post.res.PostResponse
import javax.inject.Inject

class PostResponseToPostDomainMapper @Inject constructor(): (PostResponse?) -> Post{
    override fun invoke(p1: PostResponse?): Post {
        return p1?.let {
            Post(
                title = it.title,
                postId = it.postId,
                photoUrl = it.photoUrl ?: "",
                likesCount = it.likesCount,
                commentsCount = it.commentsCount,
                createdAt = it.createdAt,
                categoryName = it.category.name,
                user = UserDataResult(userId = it.user.userId, avatarUrl = it.user.avatarUrl?:"", userName = it.user.userName)
            )
        }?:Post.EMPTY
    }

}