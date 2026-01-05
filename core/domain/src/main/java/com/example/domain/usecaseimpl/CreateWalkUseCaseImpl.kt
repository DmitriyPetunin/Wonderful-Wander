package com.example.domain.usecaseimpl

import com.example.base.model.walk.WalkCreateParam
import com.example.domain.repository.WalkRepository
import com.example.domain.usecase.CreateWalkUseCase

class CreateWalkUseCaseImpl(
    private val walkRepository: WalkRepository
): CreateWalkUseCase {
    override suspend fun invoke(walkParam: WalkCreateParam):Result<Unit> {
        return walkRepository.createWalk(walkParam)
    }
}