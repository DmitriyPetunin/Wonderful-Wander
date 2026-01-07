package com.example.data.repository

import com.example.domain.model.walk.WalkCreateParam
import com.example.domain.repository.WalkRepository
import com.example.network.model.walk.req.CreateWalkRequest
import com.example.network.service.walk.WalkService
import javax.inject.Inject

class WalkRepositoryImpl @Inject constructor(
    private val walkService: WalkService
): WalkRepository {
    override suspend fun createWalk(data: WalkCreateParam): Result<Unit> {
        return try {
            val response = walkService.createWalk(CreateWalkRequest(name = data.name, walkParticipants = data.listOfParticipants, startPointLatitude = data.startPoint.latitude, startPointLongitude = data.startPoint.longitude))
            when{
                response.isSuccessful -> {
                    Result.success(Unit)
                }
                response.code() == 400 -> { Result.failure(Exception("")) }
                else -> { Result.failure(Exception("")) }
            }
        }catch (e:Exception){
            e.printStackTrace()
            Result.failure(e)
        }
    }
}