package com.wonderfulwander.domain.usecase

import com.example.domain.model.post.Comment
import com.example.domain.model.post.UserDataResult
import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.GetAllCommentsByPostIdUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAllCommentsByPostIdUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: GetAllCommentsByPostIdUseCaseImpl

    companion object {
        private const val POST_ID = "post_123"
        private const val PAGE = 1
        private const val LIMIT = 20

        private val TEST_USER = UserDataResult(
            userId = "user_456",
            userName = "john_doe",
            avatarUrl = "avatar.jpg"
        )

        private val TEST_COMMENTS = listOf(
            Comment(
                commentId = "comment_1",
                text = "Great post!",
                user = TEST_USER,
                createdAt = "2024-01-15T10:30:00",
                repliesCount = 2
            ),
            Comment(
                commentId = "comment_2",
                text = "Beautiful photo",
                user = TEST_USER,
                createdAt = "2024-01-15T11:00:00",
                repliesCount = 0
            )
        )
    }

    @Before
    fun setUp() {
        postRepository = mockk()
        useCase = GetAllCommentsByPostIdUseCaseImpl(postRepository)
    }

    @Test
    fun `should call repository getAllCommentsByPostId with correct parameters`() = runTest {
        coEvery { postRepository.getAllCommentsByPostId(any(), any(), any()) } returns Result.success(emptyList())

        useCase.invoke(POST_ID, PAGE, LIMIT)

        coVerify(exactly = 1) {
            postRepository.getAllCommentsByPostId(POST_ID, PAGE, LIMIT)
        }
    }

    @Test
    fun `should return comments with user data when repository succeeds`() = runTest {
        coEvery { postRepository.getAllCommentsByPostId(any(), any(), any()) } returns Result.success(TEST_COMMENTS)

        val result = useCase.invoke(POST_ID, PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("Great post!", result.getOrNull()?.get(0)?.text)
        assertEquals("john_doe", result.getOrNull()?.get(0)?.user?.userName)
    }

    @Test
    fun `should return empty list when no comments exist`() = runTest {
        coEvery { postRepository.getAllCommentsByPostId(any(), any(), any()) } returns Result.success(emptyList())

        val result = useCase.invoke(POST_ID, PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}