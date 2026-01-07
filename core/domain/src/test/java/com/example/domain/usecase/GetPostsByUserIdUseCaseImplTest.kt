package com.wonderfulwander.domain.usecase

import com.example.domain.model.post.Post
import com.example.domain.model.post.UserDataResult
import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.GetPostsByUserIdUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPostsByUserIdUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: GetPostsByUserIdUseCaseImpl

    companion object {
        private const val USER_ID = "user_789"
        private const val PAGE = 1
        private const val LIMIT = 10

        private val TEST_USER = UserDataResult(
            userId = USER_ID,
            userName = "travel_lover",
            avatarUrl = "travel_avatar.jpg"
        )

        private val TEST_POSTS = listOf(
            Post(
                postId = "post_a",
                title = "Beach Sunset",
                photoUrl = "beach.jpg",
                categoryName = "Nature",
                user = TEST_USER,
                likesCount = 45,
                commentsCount = 12,
                createdAt = "2024-01-05"
            ),
            Post(
                postId = "post_b",
                title = "City Lights",
                photoUrl = "city.jpg",
                categoryName = "Urban",
                user = TEST_USER,
                likesCount = 32,
                commentsCount = 8,
                createdAt = "2024-01-08"
            )
        )
    }

    @Before
    fun setUp() {
        postRepository = mockk()
        useCase = GetPostsByUserIdUseCaseImpl(postRepository)
    }

    @Test
    fun `should call repository getPostsByUserId with correct parameters`() = runTest {
        coEvery { postRepository.getPostsByUserId(any(), any(), any()) } returns Result.success(emptyList())

        useCase.invoke(USER_ID, PAGE, LIMIT)

        coVerify(exactly = 1) {
            postRepository.getPostsByUserId(
                userId = USER_ID,
                page = PAGE,
                limit = LIMIT
            )
        }
    }

    @Test
    fun `should return user's posts with correct user info`() = runTest {
        coEvery { postRepository.getPostsByUserId(any(), any(), any()) } returns Result.success(TEST_POSTS)

        val result = useCase.invoke(USER_ID, PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("travel_lover", result.getOrNull()?.get(0)?.user?.userName)
        assertEquals("Beach Sunset", result.getOrNull()?.get(0)?.title)
        assertEquals("Nature", result.getOrNull()?.get(0)?.categoryName)
    }

    @Test
    fun `should filter posts by user ID correctly`() = runTest {
        val differentUserId = "user_999"
        coEvery { postRepository.getPostsByUserId(differentUserId, any(), any()) } returns
                Result.success(emptyList())

        val result = useCase.invoke(differentUserId, PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}