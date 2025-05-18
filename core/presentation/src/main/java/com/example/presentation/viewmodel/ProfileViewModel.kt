package com.example.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.profile.ProfileAction
import com.example.base.event.profile.ProfileEvent
import com.example.base.state.ProfileState
import com.example.base.state.SignInState
import com.example.presentation.googleclient.GoogleAuthUiClient
import com.example.presentation.usecase.GetAllFriendsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
):ViewModel() {

    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event: SharedFlow<ProfileEvent> = _event


    private fun signOut(){
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            _event.emit(ProfileEvent.NavigateToAuthPage)
        }
        resetState()
    }


    fun onAction(action: ProfileAction) {
        when(action){
            ProfileAction.SignOut -> {

            }
            ProfileAction.SubmitGetAllFriends -> TODO()
        }
    }

    fun getSignedInUser() {
        val userData = googleAuthUiClient.getSignedInUser()
        userData?.let {
            _state.update {
                it.copy(
                    userId = userData.userId,
                    username = userData.username,
                    profilePictureUrl = userData.profilePictureUrl
                )
            }
        }
    }


    private fun resetState() {
        _state.update { ProfileState() }
    }
}