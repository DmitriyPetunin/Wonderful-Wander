package com.example.domain.usecase

import com.example.domain.model.user.profile.PersonProfileInfoResult


interface GetPersonProfileInfoByIdUseCase {
    suspend fun invoke(id:String):Result<PersonProfileInfoResult>
}