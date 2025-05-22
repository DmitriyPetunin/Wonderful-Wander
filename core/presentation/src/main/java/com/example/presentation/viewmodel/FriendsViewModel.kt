package com.example.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.base.action.profile.FriendsPageAction
import com.example.base.model.user.friends.Friend
import com.example.base.state.ListScreenState
import com.example.presentation.usecase.GetAllFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val getAllFriendsUseCase: GetAllFriendsUseCase,
) : BaseListViewModel<Friend>() {

    private val _state = MutableStateFlow(ListScreenState<Friend>())
    val state: StateFlow<ListScreenState<Friend>> = _state.asStateFlow()


    override suspend fun loadData() {

        val response = getAllFriendsUseCase.invoke()

        _state.update { currentState ->
            response.fold(
                onSuccess = { list: List<Friend> ->
                    if (list.isEmpty()) {
                        currentState
                    } else currentState.copy(listOfPeople = list)
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                    currentState
                }
            )
        }
    }

    fun onAction(action: FriendsPageAction){
        when(action){
            is FriendsPageAction.SubmitBackButton -> {

            }
            is FriendsPageAction.SubmitFriendsItem -> {

            }
        }

    }
}