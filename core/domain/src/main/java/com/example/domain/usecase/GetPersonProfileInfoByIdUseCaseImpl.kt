package com.example.domain.usecase

import com.example.base.model.user.profile.PersonProfileInfoResult
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.GetPersonProfileInfoByIdUseCase
import javax.inject.Inject

class GetPersonProfileInfoByIdUseCaseImpl(
    private val userRepository: UserRepository
): GetPersonProfileInfoByIdUseCase {
    override suspend fun invoke(id: String): Result<PersonProfileInfoResult> {
        return userRepository.getPersonProfileInfoById(id)
    }
}