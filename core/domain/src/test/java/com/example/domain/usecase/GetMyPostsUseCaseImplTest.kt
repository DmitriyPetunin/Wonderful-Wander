package com.wonderfulwander.domain.usecase
import com.example.domain.model.post.Post
import com.example.domain.model.post.UserDataResult
import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.GetMyPostsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetMyPostsUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: GetMyPostsUseCaseImpl

    companion object {
        private const val PAGE = 1
        private const val LIMIT = 10

        private val TEST_USER = UserDataResult(
            userId = "me",
            userName = "my_username",
            avatarUrl = "my_avatar.jpg"
        )

        private val TEST_POSTS = listOf(
            Post(
                postId = "post_1",
                title = "My First Post",
                photoUrl = "photo1.jpg",
                categoryName = "Nature",
                user = TEST_USER,
                likesCount = 10,
                commentsCount = 3,
                createdAt = "2024-01-10"
            ),
            Post(
                postId = "post_2",
                title = "Mountain Trip",
                photoUrl = "photo2.jpg",
                categoryName = "Travel",
                user = TEST_USER,
                likesCount = 25,
                commentsCount = 7,
                createdAt = "2024-01-12"
            )
        )
    }

    @Before
    fun setUp() {
        postRepository = mockk()
        useCase = GetMyPostsUseCaseImpl(postRepository)
    }

    @Test
    fun `should call repository getMyPosts with correct parameters`() = runTest {
        coEvery { postRepository.getMyPosts(any(), any()) } returns Result.success(emptyList())

        useCase.invoke(PAGE, LIMIT)

        coVerify(exactly = 1) {
            postRepository.getMyPosts(PAGE, LIMIT)
        }
    }

    @Test
    fun `should return posts with correct user info when repository succeeds`() = runTest {
        coEvery { postRepository.getMyPosts(any(), any()) } returns Result.success(TEST_POSTS)

        val result = useCase.invoke(PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("my_username", result.getOrNull()?.get(0)?.user?.userName)
        assertEquals("Nature", result.getOrNull()?.get(0)?.categoryName)
        assertEquals(10, result.getOrNull()?.get(0)?.likesCount)
    }

    @Test
    fun `should return empty list when user has no posts`() = runTest {
        coEvery { postRepository.getMyPosts(any(), any()) } returns Result.success(emptyList())

        val result = useCase.invoke(PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}