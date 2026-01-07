package com.wonderfulwander.domain.usecase

import com.example.base.enums.PhotosVisibility
import com.example.base.enums.Role
import com.example.base.enums.WalkVisibility
import com.example.domain.model.user.profile.ProfileInfoResult
import com.example.domain.model.user.profile.UpdateProfileParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecaseimpl.UpdateProfileInfoUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UpdateProfileInfoUseCaseImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var useCase: UpdateProfileInfoUseCaseImpl

    private val testUpdateParam = UpdateProfileParam(
        email = "updated@example.com",
        firstName = "Updated",
        lastName = "Name",
        bio = "Updated bio",
        myPhotoVisibility = PhotosVisibility.FRIENDS_ONLY,
        savedPhotoVisibility = PhotosVisibility.PRIVATE,
        walkVisibility = WalkVisibility.PUBLIC
    )

    private val testProfileInfo = ProfileInfoResult(
        userId = "user_123",
        username = "updated_user",
        email = "updated@example.com",
        firstname = "Updated",
        lastname = "Name",
        bio = "Updated bio",
        role = Role.ROLE_USER,
        avatarUrl = "avatar.jpg",
        followersCount = 100,
        followingCount = 150,
        friendsCount = 50,
        myPhotoVisibility = PhotosVisibility.FRIENDS_ONLY,
        savedPhotoVisibility = PhotosVisibility.PRIVATE,
        walkVisibility = WalkVisibility.PUBLIC
    )

    @Before
    fun setUp() {
        userRepository = mockk()
        useCase = UpdateProfileInfoUseCaseImpl(userRepository)
    }

    @Test
    fun `should call repository getProfileInfo`() = runTest {
        coEvery { userRepository.getProfileInfo() } returns Result.success(testProfileInfo)

        useCase.invoke(testUpdateParam)

        coVerify(exactly = 1) {
            userRepository.getProfileInfo()
        }
    }

    @Test
    fun `should return updated profile info when repository succeeds`() = runTest {
        coEvery { userRepository.getProfileInfo() } returns Result.success(testProfileInfo)

        val result = useCase.invoke(testUpdateParam)

        assertTrue(result.isSuccess)
        val profile = result.getOrNull()
        assertEquals("updated_user", profile?.username)
        assertEquals("updated@example.com", profile?.email)
        assertEquals("Updated", profile?.firstname)
        assertEquals("Updated bio", profile?.bio)
        assertEquals(PhotosVisibility.FRIENDS_ONLY, profile?.myPhotoVisibility)
        assertEquals(PhotosVisibility.PRIVATE, profile?.savedPhotoVisibility)
    }

    @Test
    fun `should handle visibility settings correctly`() = runTest {
        val paramWithPublicVisibility = testUpdateParam.copy(
            myPhotoVisibility = PhotosVisibility.PUBLIC,
            walkVisibility = WalkVisibility.PUBLIC
        )

        val profileWithPublic = testProfileInfo.copy(
            myPhotoVisibility = PhotosVisibility.PUBLIC,
            walkVisibility = WalkVisibility.PUBLIC
        )

        coEvery { userRepository.getProfileInfo() } returns Result.success(profileWithPublic)

        val result = useCase.invoke(paramWithPublicVisibility)

        assertTrue(result.isSuccess)
        assertEquals(PhotosVisibility.PUBLIC, result.getOrNull()?.myPhotoVisibility)
    }
}