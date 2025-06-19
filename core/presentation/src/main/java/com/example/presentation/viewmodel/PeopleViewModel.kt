package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.profile.PeoplePageAction
import com.example.base.event.people.PeoplePageEvent
import com.example.base.model.user.People
import com.example.base.state.ListScreenState
import com.example.base.state.PeopleEnum
import com.example.presentation.usecase.GetAllFollowersUseCase
import com.example.presentation.usecase.GetAllFollowingUseCase
import com.example.presentation.usecase.GetAllFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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


    private fun loadPeople() {
        Log.d("STATE","currentPage = ${state.value.currentPage}")
        if ((!state.value.isInitialLoading && state.value.isLoading) || state.value.endReached) return

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1000L)
            val result = when(state.value.people){
                PeopleEnum.FRIENDS -> { getAllFriendsUseCase.invoke(page = state.value.currentPage, limit = state.value.limit)}
                PeopleEnum.FOLLOWING -> { getAllFollowingUseCase.invoke(page = state.value.currentPage, limit = state.value.limit) }
                PeopleEnum.FOLLOWERS -> { getAllFollowersUseCase.invoke(page = state.value.currentPage, limit = state.value.limit) }
            }

            _state.update { currentState ->
                result.fold(
                    onSuccess = { newPeople: List<People> ->
                        if(!state.value.isInitialLoading && newPeople.lastOrNull() == state.value.listOfPeople.lastOrNull()){
                            currentState.copy(
                                isLoading = false,
                                endReached = true
                            )
                        } else {
                            currentState.copy(
                                listOfPeople = currentState.listOfPeople + newPeople,
                                isLoading = false,
                                currentPage = currentState.currentPage + 1,
                                endReached = newPeople.size < currentState.limit
                            )
                        }
                    },
                    onFailure = { exception ->
                        exception.printStackTrace()
                        currentState.copy(isLoading = false)
                    }
                )
            }
            Log.d("TEST-TAG","isLoading = ${state.value.isLoading}")
        }
    }

    fun onAction(action: PeoplePageAction) {
        when(action){
            is PeoplePageAction.SubmitBackButton -> {

            }
            is PeoplePageAction.SubmitPersonItem -> {
                resetState()
                viewModelScope.launch {
                    _event.emit(PeoplePageEvent.NavigateToPersonProfileWithUserId(userId = action.userId))
                }
            }

            PeoplePageAction.LoadMore -> {
                Log.d("LoadMore","LoadMore")
                updateEndReachedState()
                loadPeople()
            }

            is PeoplePageAction.UpdatePeopleState -> {
                Log.d("UpdateEndReache","listOfPeople.size = ${state.value.listOfPeople.size}")
                updatePeopleState(action.input)
                if(state.value.isInitialLoading) {
                    loadPeople()
                    updateInitLoadingState()
                }
            }
        }

    }

    private fun resetState(){
        _state.update {
            it.copy(
                listOfPeople = emptyList(),
                currentPage = 1,
                endReached = false,
                isInitialLoading = true,
            )
        }
    }
    private fun updateInitLoadingState(){
        _state.update {
            it.copy(isInitialLoading = false)
        }
    }
    private fun updatePeopleState(input:PeopleEnum){
        _state.update {
            it.copy(people = input)
        }
    }

    private fun updateEndReachedState(){
        _state.update {
            it.copy(endReached = false)
        }
    }
}