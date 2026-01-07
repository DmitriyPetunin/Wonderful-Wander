package com.wonderfulwander.domain.usecase

import com.example.domain.model.post.Post
import com.example.domain.model.post.UserDataResult
import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.GetSavedPostsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetSavedPostsUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: GetSavedPostsUseCaseImpl

    companion object {
        private const val PAGE = 1
        private const val LIMIT = 10

        private val TEST_USER = UserDataResult(
            userId = "author_1",
            userName = "post_author",
            avatarUrl = "author_avatar.jpg"
        )

        private val TEST_POSTS = listOf(
            Post(
                postId = "saved_1",
                title = "Saved Post 1",
                photoUrl = "saved1.jpg",
                categoryName = "Nature",
                user = TEST_USER,
                likesCount = 15,
                commentsCount = 5,
                createdAt = "2024-01-01"
            ),
            Post(
                postId = "saved_2",
                title = "Saved Post 2",
                photoUrl = "saved2.jpg",
                categoryName = "Travel",
                user = TEST_USER,
                likesCount = 30,
                commentsCount = 10,
                createdAt = "2024-01-02"
            )
        )
    }

    @Before
    fun setUp() {
        postRepository = mockk()
        useCase = GetSavedPostsUseCaseImpl(postRepository)
    }

    @Test
    fun `should call repository getSavedPosts with correct parameters`() = runTest {
        coEvery { postRepository.getSavedPosts(any(), any()) } returns Result.success(emptyList())

        useCase.invoke(PAGE, LIMIT)

        coVerify(exactly = 1) {
            postRepository.getSavedPosts(PAGE, LIMIT)
        }
    }

    @Test
    fun `should return saved posts with complete info`() = runTest {
        coEvery { postRepository.getSavedPosts(any(), any()) } returns Result.success(TEST_POSTS)

        val result = useCase.invoke(PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("Saved Post 1", result.getOrNull()?.get(0)?.title)
        assertEquals("post_author", result.getOrNull()?.get(0)?.user?.userName)
        assertEquals("Nature", result.getOrNull()?.get(0)?.categoryName)
        assertEquals(15, result.getOrNull()?.get(0)?.likesCount)
    }

    @Test
    fun `should handle empty saved posts list`() = runTest {
        coEvery { postRepository.getSavedPosts(any(), any()) } returns Result.success(emptyList())

        val result = useCase.invoke(PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}