package com.wonderfulwander.domain.usecase

import com.example.domain.model.post.PostCreateParam
import com.example.domain.repository.PostRepository
import com.example.domain.usecaseimpl.CreatePostUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CreatePostUseCaseImplTest {

    private lateinit var postRepository: PostRepository
    private lateinit var useCase: CreatePostUseCaseImpl

    private val testPostParam = PostCreateParam(
        title = "Beautiful Sunset",
        imageFilename = "sunset.jpg",
        categoryId = 1L
    )

    @Before
    fun setUp() {
        postRepository = mockk()
        useCase = CreatePostUseCaseImpl(postRepository)
    }

    @Test
    fun `should call repository createPost with correct parameters`() = runTest {
        coEvery { postRepository.createPost(any()) } returns Result.success(Unit)

        useCase.invoke(testPostParam)

        coVerify(exactly = 1) {
            postRepository.createPost(testPostParam)
        }
    }

    @Test
    fun `should return success when post creation succeeds`() = runTest {
        coEvery { postRepository.createPost(any()) } returns Result.success(Unit)

        val result = useCase.invoke(testPostParam)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `should handle empty title`() = runTest {
        val paramWithEmptyTitle = testPostParam.copy(title = "")
        coEvery { postRepository.createPost(paramWithEmptyTitle) } returns
                Result.failure(IllegalArgumentException("Title cannot be empty"))

        val result = useCase.invoke(paramWithEmptyTitle)

        assertTrue(result.isFailure)
    }
}