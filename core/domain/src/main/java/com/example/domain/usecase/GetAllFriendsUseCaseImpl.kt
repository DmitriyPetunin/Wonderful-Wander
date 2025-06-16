package com.example.domain.usecase

import android.util.Log
import com.example.base.model.user.People
import com.example.domain.repository.UserRepository
import com.example.presentation.usecase.GetAllFriendsUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetAllFriendsUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
): GetAllFriendsUseCase {
    override suspend fun invoke(page:Int,limit:Int): Result<List<People>> {
        
        return userRepository.getAllFriends(page,limit)
    }
}