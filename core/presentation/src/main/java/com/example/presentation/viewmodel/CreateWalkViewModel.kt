package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.walk.CreateWalkAction
import com.example.base.model.user.People
import com.example.base.state.CreateWalkState
import com.example.presentation.usecase.GetAllFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWalkViewModel @Inject constructor(
    private val getAllFriendsUseCase: GetAllFriendsUseCase
):ViewModel() {

    private val _state = MutableStateFlow(CreateWalkState())
    val state = _state.asStateFlow()

    fun onAction(action: CreateWalkAction){
        when(action) {
            is CreateWalkAction.UpdateQueryParam -> {
                updateQueryParam(action.input)
            }
            is CreateWalkAction.GetAllFriends -> {
                getAllFriends()
            }
        }
    }

    private fun getAllFriends(){
        viewModelScope.launch {
            val response = getAllFriendsUseCase.invoke()

            _state.update { currentState ->
                response.fold(
                    onSuccess = { list: List<People> ->
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
    }

    private fun updateQueryParam(input: String){
        _state.update {
            it.copy(queryParam = input)
        }
    }
}