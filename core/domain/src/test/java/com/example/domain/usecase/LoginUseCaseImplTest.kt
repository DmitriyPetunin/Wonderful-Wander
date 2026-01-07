package com.wonderfulwander.domain.usecase

import com.example.domain.model.user.login.LoginResult
import com.example.domain.model.user.login.LoginUserParam
import com.example.domain.repository.UserRepository
import com.example.domain.usecaseimpl.LoginUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var useCase: LoginUseCaseImpl

    private val testLoginParam = LoginUserParam(
        email = "user@example.com",
        password = "securePassword123"
    )

    private val testLoginResult = LoginResult(
        status = "SUCCESS"
    )

    @Before
    fun setUp() {
        userRepository = mockk()
        useCase = LoginUseCaseImpl(userRepository)
    }

    @Test
    fun `should call repository login with correct parameters`() = runTest {
        coEvery { userRepository.login(any()) } returns Result.success(testLoginResult)

        useCase.invoke(testLoginParam)

        coVerify(exactly = 1) {
            userRepository.login(testLoginParam)
        }
    }

    @Test
    fun `should return success status when authentication succeeds`() = runTest {
        coEvery { userRepository.login(any()) } returns Result.success(testLoginResult)

        val result = useCase.invoke(testLoginParam)

        assertTrue(result.isSuccess)
        assertEquals("SUCCESS", result.getOrNull()?.status)
    }

    @Test
    fun `should return failure status when authentication fails`() = runTest {
        val failedLoginResult = LoginResult(status = "FAILED")
        coEvery { userRepository.login(any()) } returns Result.success(failedLoginResult)

        val result = useCase.invoke(testLoginParam)

        assertTrue(result.isSuccess)
        assertEquals("FAILED", result.getOrNull()?.status)
    }

    @Test
    fun `should handle invalid email format`() = runTest {
        val invalidEmailParam = testLoginParam.copy(email = "invalid-email")
        coEvery { userRepository.login(invalidEmailParam) } returns
                Result.failure(IllegalArgumentException("Invalid email format"))

        val result = useCase.invoke(invalidEmailParam)

        assertTrue(result.isFailure)
    }
}