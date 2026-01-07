package com.wonderfulwander.domain.usecase

import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.DeletePostFromMySavedPostsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeletePostFromMySavedPostsUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: DeletePostFromMySavedPostsUseCaseImpl

    private val POST_ID = "post_123"

    @Before
    fun setUp() {
        postRepository = mockk()
        useCase = DeletePostFromMySavedPostsUseCaseImpl(postRepository)
    }

    @Test
    fun `should call repository deletePostFromMySavedPosts with correct postId`() = runTest {
        coEvery { postRepository.deletePostFromMySavedPosts(any()) } returns Result.success(Unit)

        useCase.invoke(POST_ID)

        coVerify(exactly = 1) { postRepository.deletePostFromMySavedPosts(postId = POST_ID) }
    }
}