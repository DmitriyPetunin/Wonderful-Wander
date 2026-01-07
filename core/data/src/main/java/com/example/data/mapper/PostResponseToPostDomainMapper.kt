package com.example.data.mapper

import com.example.domain.model.post.Post
import com.example.domain.model.post.UserDataResult
import com.example.network.model.post.res.PostResponse
import javax.inject.Inject

class PostResponseToPostDomainMapper @Inject constructor(): (PostResponse?) -> com.example.domain.model.post.Post {
    override fun invoke(p1: PostResponse?): com.example.domain.model.post.Post {
        return p1?.let {
            _root_ide_package_.com.example.domain.model.post.Post(
                title = it.title,
                postId = it.postId,
                photoUrl = it.photoUrl ?: "",
                likesCount = it.likesCount,
                commentsCount = it.commentsCount,
                createdAt = it.createdAt,
                categoryName = it.category.name,
                user = _root_ide_package_.com.example.domain.model.post.UserDataResult(
                    userId = it.user.userId,
                    avatarUrl = it.user.avatarUrl ?: "",
                    userName = it.user.userName
                )
            )
        }?: _root_ide_package_.com.example.domain.model.post.Post.EMPTY
    }

}