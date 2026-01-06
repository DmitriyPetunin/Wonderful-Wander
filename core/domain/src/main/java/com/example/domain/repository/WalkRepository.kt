package com.example.domain.repository

import com.example.domain.model.walk.WalkCreateParam


interface WalkRepository {
    suspend fun createWalk(data: WalkCreateParam):Result<Unit>
}