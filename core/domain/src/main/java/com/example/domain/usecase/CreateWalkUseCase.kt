package com.example.domain.usecase

import com.example.base.model.walk.WalkCreateParam

interface CreateWalkUseCase {
    suspend fun invoke(walkParam: WalkCreateParam):Result<Unit>
}