package com.wonderfulwander.domain.usecase

import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.DeletePostFromMyPostsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class DeletePostFromMyPostsUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: DeletePostFromMyPostsUseCaseImpl

    private val POST_ID = "post_123"

    @Before
    fun setUp() {
        postRepository = mockk()
        useCase = DeletePostFromMyPostsUseCaseImpl(postRepository)
    }

    @Test
    fun `should call repository deletePostFromMyPosts with correct postId`() = runTest {
        coEvery { postRepository.deletePostFromMyPosts(any()) } returns Result.success(Unit)

        useCase.invoke(POST_ID)

        coVerify(exactly = 1) { postRepository.deletePostFromMyPosts(postId = POST_ID) }
    }
}