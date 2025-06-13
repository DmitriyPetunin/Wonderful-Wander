package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.profile.PeoplePageAction
import com.example.base.event.people.PeoplePageEvent
import com.example.base.model.user.People
import com.example.base.state.ListScreenState
import com.example.presentation.usecase.GetAllFollowersUseCase
import com.example.presentation.usecase.GetAllFollowingUseCase
import com.example.presentation.usecase.GetAllFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val getAllFriendsUseCase: GetAllFriendsUseCase,
    private val getAllFollowersUseCase: GetAllFollowersUseCase,
    private val getAllFollowingUseCase: GetAllFollowingUseCase
):ViewModel()  {

    private val _state = MutableStateFlow(ListScreenState())
    val state: StateFlow<ListScreenState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<PeoplePageEvent>()
    val event:SharedFlow<PeoplePageEvent> = _event


    fun loadFriends() {
        viewModelScope.launch {
            val response = getAllFriendsUseCase.invoke()

            _state.update { currentState ->
                response.fold(
                    onSuccess = { list: List<People> ->
                        if (list.isEmpty()) {
                            currentState
                        } else currentState.copy(listOfPeople = list, people = "Friends")
                    },
                    onFailure = { exception ->
                        exception.printStackTrace()
                        currentState
                    }
                )
            }
        }
    }

    fun loadFollowers() {
        viewModelScope.launch {
            val response = getAllFollowersUseCase.invoke()

            _state.update { currentState ->
                response.fold(
                    onSuccess = { list: List<People> ->
                        if (list.isEmpty()) {
                            currentState
                        } else currentState.copy(listOfPeople = list,people = "Followers")
                    },
                    onFailure = { exception ->
                        exception.printStackTrace()
                        currentState
                    }
                )
            }
        }
    }
    fun loadFollowing() {
        viewModelScope.launch {
            val response = getAllFollowingUseCase.invoke()

            _state.update { currentState ->
                response.fold(
                    onSuccess = { list: List<People> ->
                        if (list.isEmpty()) {
                            currentState
                        } else currentState.copy(listOfPeople = list, people = "Following")
                    },
                    onFailure = { exception ->
                        exception.printStackTrace()
                        currentState
                    }
                )
            }
        }
    }

    fun onAction(action: PeoplePageAction){
        when(action){
            is PeoplePageAction.SubmitBackButton -> {

            }
            is PeoplePageAction.SubmitPersonItem -> {
                viewModelScope.launch {
                    _event.emit(PeoplePageEvent.NavigateToPersonProfileWithUserId(userInfo = action.userInfo))
                }
            }
        }

    }
}