package com.example.presentation.usecase

import com.example.base.model.user.profile.PersonProfileInfoResult

interface GetPersonProfileInfoByIdUseCase {
    suspend fun invoke(id:String):Result<PersonProfileInfoResult>
}