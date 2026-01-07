package com.wonderfulwander.domain.usecase

import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.DeleteCommentUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeleteCommentUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: DeleteCommentUseCaseImpl

    companion object {
        private const val POST_ID = "post_123"
        private const val COMMENT_ID = "comment_456"
    }

    @Before
    fun setUp() {
        postRepository = mockk()
        useCase = DeleteCommentUseCaseImpl(postRepository)
    }

    @Test
    fun `should call repository deleteComment with correct parameters`() = runTest {
        coEvery { postRepository.deleteComment(any(), any()) } returns Result.success(Unit)

        useCase.invoke(POST_ID, COMMENT_ID)

        coVerify(exactly = 1) {
            postRepository.deleteComment(postId = POST_ID, commentId = COMMENT_ID)
        }
    }

    @Test
    fun `should return success when comment deletion succeeds`() = runTest {
        coEvery { postRepository.deleteComment(any(), any()) } returns Result.success(Unit)

        val result = useCase.invoke(POST_ID, COMMENT_ID)

        assertTrue(result.isSuccess)
    }
}