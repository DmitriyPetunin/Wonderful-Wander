package com.example.domain.usecaseimpl

import com.example.base.model.user.profile.PersonProfileInfoResult
import com.example.domain.repository.UserRepository
import com.example.domain.usecase.GetPersonProfileInfoByIdUseCase

class GetPersonProfileInfoByIdUseCaseImpl(
    private val userRepository: UserRepository
): GetPersonProfileInfoByIdUseCase {
    override suspend fun invoke(id: String): Result<PersonProfileInfoResult> {
        return userRepository.getPersonProfileInfoById(id)
    }
}