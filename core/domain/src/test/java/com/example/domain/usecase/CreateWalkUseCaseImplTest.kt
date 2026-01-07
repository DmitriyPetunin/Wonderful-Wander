package com.wonderfulwander.domain.usecase

import com.example.domain.model.walk.Point
import com.example.domain.model.walk.WalkCreateParam
import com.example.domain.repository.WalkRepository
import com.example.domain.usecaseimpl.CreateWalkUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CreateWalkUseCaseImplTest {

    private lateinit var walkRepository: WalkRepository
    private lateinit var useCase: CreateWalkUseCaseImpl

    private val testWalkParam = WalkCreateParam(
        name = "Morning Walk",
        listOfParticipants = listOf("user1", "user2"),
        startPoint = Point(latitude = 55.7558, longitude = 37.6173)
    )

    @Before
    fun setUp() {
        walkRepository = mockk()
        useCase = CreateWalkUseCaseImpl(walkRepository)
    }

    @Test
    fun `should call repository createWalk with correct parameters`() = runTest {
        coEvery { walkRepository.createWalk(any()) } returns Result.success(Unit)

        useCase.invoke(testWalkParam)

        coVerify(exactly = 1) {
            walkRepository.createWalk(testWalkParam)
        }
    }

    @Test
    fun `should return success when walk creation succeeds`() = runTest {
        coEvery { walkRepository.createWalk(any()) } returns Result.success(Unit)

        val result = useCase.invoke(testWalkParam)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `should handle empty participants list`() = runTest {
        val paramWithEmptyList = testWalkParam.copy(listOfParticipants = emptyList())
        coEvery { walkRepository.createWalk(paramWithEmptyList) } returns Result.success(Unit)

        val result = useCase.invoke(paramWithEmptyList)

        assertTrue(result.isSuccess)
    }
}