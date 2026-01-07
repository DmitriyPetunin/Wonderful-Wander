package com.wonderfulwander.domain.usecase

import com.example.domain.model.user.profile.PersonProfileInfoResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecaseimpl.GetPersonProfileInfoByIdUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetPersonProfileInfoByIdUseCaseImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var useCase: GetPersonProfileInfoByIdUseCaseImpl

    private val USER_ID = "user_123"

    private val testProfileInfo = PersonProfileInfoResult(
        userId = USER_ID,
        userName = "john_doe",
        firstname = "John",
        lastname = "Doe",
        bio = "Travel enthusiast",
        avatarUrl = "avatar.jpg",
        followersCount = 150,
        followingCount = 200,
        friendsCount = 50,
        isFollowedByUser = true,
        isFollowingByUser = false,
        isFriends = false
    )

    @Before
    fun setUp() {
        userRepository = mockk()
        useCase = GetPersonProfileInfoByIdUseCaseImpl(userRepository)
    }

    @Test
    fun `should call repository getPersonProfileInfoById with correct id`() = runTest {
        coEvery { userRepository.getPersonProfileInfoById(any()) } returns Result.success(testProfileInfo)

        useCase.invoke(USER_ID)

        coVerify(exactly = 1) {
            userRepository.getPersonProfileInfoById(USER_ID)
        }
    }

    @Test
    fun `should return complete profile info when repository succeeds`() = runTest {
        coEvery { userRepository.getPersonProfileInfoById(any()) } returns Result.success(testProfileInfo)

        val result = useCase.invoke(USER_ID)

        assertTrue(result.isSuccess)
        val profile = result.getOrNull()
        assertEquals(USER_ID, profile?.userId)
        assertEquals("John", profile?.firstname)
        assertEquals("Doe", profile?.lastname)
        assertEquals(150, profile?.followersCount)
        assertEquals(true, profile?.isFollowedByUser)
        assertEquals(false, profile?.isFriends)
    }

    @Test
    fun `should handle non-existent user id`() = runTest {
        val nonExistentId = "non_existent_999"
        coEvery { userRepository.getPersonProfileInfoById(nonExistentId) } returns
                Result.failure(Exception("User not found"))

        val result = useCase.invoke(nonExistentId)

        assertTrue(result.isFailure)
    }
}