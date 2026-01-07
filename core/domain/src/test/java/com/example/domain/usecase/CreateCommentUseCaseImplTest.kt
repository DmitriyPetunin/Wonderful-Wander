package com.wonderfulwander.domain.usecase

import com.example.domain.model.post.CommentCreateParam
import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.CreateCommentUseCaseImpl
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CreateCommentUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: CreateCommentUseCaseImpl

    companion object {
        private const val VALID_POST_ID = "post_123"
        private const val VALID_USER_ID = "user_456"
        private const val VALID_COMMENT_TEXT = "Great post!"

        private val VALID_COMMENT_DATA = CommentCreateParam(
            text = VALID_COMMENT_TEXT,
            parentCommentId = VALID_USER_ID
        )

        private const val NETWORK_ERROR = "Network error"
        private const val DATABASE_ERROR = "Database error"
    }

    @Before
    fun setUp() {
        postRepository = mockk(relaxed = true)
        useCase = CreateCommentUseCaseImpl(postRepository)
        clearAllMocks()
    }

    @Test
    fun `should call repository with correct parameters`() = runTest {
        coEvery { postRepository.createComment(any(), any()) } returns Result.success(Unit)

        useCase.invoke(VALID_POST_ID, VALID_COMMENT_DATA)

        coVerify(exactly = 1) {
            postRepository.createComment(
                postId = VALID_POST_ID,
                text = VALID_COMMENT_DATA
            )
        }
    }

    @Test
    fun `should return success when repository succeeds`() = runTest {
        coEvery { postRepository.createComment(any(), any()) } returns Result.success(Unit)

        val result = useCase.invoke(VALID_POST_ID, VALID_COMMENT_DATA)

        assertTrue("Expected success result", result.isSuccess)
        result.onSuccess {
            assertEquals(Unit, it)
        }
    }

    @Test
    fun `should return failure when repository fails`() = runTest {
        val expectedError = RuntimeException(NETWORK_ERROR)
        coEvery { postRepository.createComment(any(), any()) } returns Result.failure(expectedError)

        val result = useCase.invoke(VALID_POST_ID, VALID_COMMENT_DATA)

        assertTrue("Expected failure result", result.isFailure)
        result.onFailure { error ->
            assertEquals(expectedError, error)
            assertEquals(NETWORK_ERROR, error.message)
        }
    }

    @Test
    fun `should not modify repository error`() = runTest {
        val originalError = IllegalStateException(DATABASE_ERROR)
        coEvery { postRepository.createComment(VALID_POST_ID, VALID_COMMENT_DATA) } returns
                Result.failure(originalError)

        val result = useCase.invoke(VALID_POST_ID, VALID_COMMENT_DATA)

        result.onFailure { error ->
            assertSame("Error should be the same instance", originalError, error)
        }
    }
}