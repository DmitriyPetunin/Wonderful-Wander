package com.wonderfulwander.domain.usecase

import com.example.domain.model.geo.ActualGeoLocationResult
import com.example.domain.repository.GeoRepository
import com.example.domain.usecaseimpl.GetActualGeoDataUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetActualGeoDataUseCaseImplTest {

    private lateinit var geoRepository: GeoRepository
    private lateinit var useCase: GetActualGeoDataUseCaseImpl

    private val GEOCODE_STRING = "Москва, Красная площадь"

    private val testGeoResult = ActualGeoLocationResult(
        text = "Москва, Россия"
    )

    @Before
    fun setUp() {
        geoRepository = mockk()
        useCase = GetActualGeoDataUseCaseImpl(geoRepository)
    }

    @Test
    fun `should call repository getActualGeoData with correct geocode string`() = runTest {
        coEvery { geoRepository.getActualGeoData(any()) } returns Result.success(testGeoResult)

        useCase.invoke(GEOCODE_STRING)

        coVerify(exactly = 1) {
            geoRepository.getActualGeoData(GEOCODE_STRING)
        }
    }

    @Test
    fun `should return geo text when repository succeeds`() = runTest {
        coEvery { geoRepository.getActualGeoData(any()) } returns Result.success(testGeoResult)

        val result = useCase.invoke(GEOCODE_STRING)

        assertTrue(result.isSuccess)
        assertEquals("Москва, Россия", result.getOrNull()?.text)
    }

    @Test
    fun `should handle empty geocode string`() = runTest {
        val emptyGeocode = ""
        coEvery { geoRepository.getActualGeoData(emptyGeocode) } returns
                Result.failure(IllegalArgumentException("Geocode string cannot be empty"))

        val result = useCase.invoke(emptyGeocode)

        assertTrue(result.isFailure)
    }
}