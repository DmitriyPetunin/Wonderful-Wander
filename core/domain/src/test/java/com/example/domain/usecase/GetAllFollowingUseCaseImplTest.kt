package com.wonderfulwander.domain.usecase

import com.example.domain.model.user.People
import com.example.domain.repository.UserRepository
import com.example.domain.usecaseimpl.GetAllFollowingUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetAllFollowingUseCaseImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var useCase: GetAllFollowingUseCaseImpl

    companion object {
        private const val PAGE = 1
        private const val LIMIT = 20

        private val TEST_PEOPLE = listOf(
            People(
                userId = "user_1",
                avatarUrl = "avatar1.jpg",
                username = "alice_wanderer"
            ),
            People(
                userId = "user_2",
                avatarUrl = "avatar2.jpg",
                username = "bob_traveler"
            )
        )
    }

    @Before
    fun setUp() {
        userRepository = mockk()
        useCase = GetAllFollowingUseCaseImpl(userRepository)
    }

    @Test
    fun `should call repository getAllFollowing with correct parameters`() = runTest {
        coEvery { userRepository.getAllFollowing(any(), any()) } returns Result.success(emptyList())

        useCase.invoke(PAGE, LIMIT)

        coVerify(exactly = 1) {
            userRepository.getAllFollowing(PAGE, LIMIT)
        }
    }

    @Test
    fun `should return people list with user info when repository succeeds`() = runTest {
        coEvery { userRepository.getAllFollowing(any(), any()) } returns Result.success(TEST_PEOPLE)

        val result = useCase.invoke(PAGE, LIMIT)

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()?.size)
        assertEquals("alice_wanderer", result.getOrNull()?.get(0)?.username)
        assertEquals("bob_traveler", result.getOrNull()?.get(1)?.username)
    }

    @Test
    fun `should handle pagination correctly`() = runTest {
        val page2 = 2
        val limit5 = 5
        coEvery { userRepository.getAllFollowing(page2, limit5) } returns Result.success(TEST_PEOPLE)

        val result = useCase.invoke(page2, limit5)

        coVerify { userRepository.getAllFollowing(page2, limit5) }
        assertTrue(result.isSuccess)
    }
}