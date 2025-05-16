package com.example.presentation.viewmodel


import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.profile.ProfileAction
import com.example.base.event.profile.ProfileEvent
import com.example.base.state.SignInResult
import com.example.base.state.SignInState
import com.example.base.state.UserData
import com.example.presentation.googleclient.GoogleAuthUiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
    @ApplicationContext val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private val _userdata = MutableStateFlow<UserData?>(null)
    val userData = _userdata.asStateFlow()

    private val _signInIntentSender = MutableStateFlow<IntentSender?>(null)
    val signInIntentSender = _signInIntentSender.asStateFlow()


    private val _signInResult = MutableStateFlow<SignInResult?>(null)
    val signInResult = _signInResult.asStateFlow()


    private val _event = MutableSharedFlow<ProfileEvent>()
    val event: SharedFlow<ProfileEvent> = _event


    private fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }


    fun signInWithIntent(intent: Intent) {
        viewModelScope.launch {
            val result = googleAuthUiClient.signInWithIntent(intent = intent)
            if (result.data != null) {
                _signInResult.value = result
                onSignInResult(result)
            }
        }
    }

    fun resetState() {
        _state.update { SignInState() }
    }

    fun signIn() {
        viewModelScope.launch {
            _signInIntentSender.value = googleAuthUiClient.signIn()
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            googleAuthUiClient.login(email, password)
        }

    }

    fun register(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _state.update {
                it.copy(
                    isSignInSuccessful = false,
                    signInError = "какая-то фигня"
                )
            }
            return
        }
        viewModelScope.launch {
            // useCase.invoke()
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            googleAuthUiClient.signOut()
            _event.emit(ProfileEvent.NavigateToAuthPage)
            _userdata.value = null
        }
    }

    fun getSignedInUser() {
        _userdata.value = googleAuthUiClient.getSignedInUser()
    }


    fun onAction(action: ProfileAction){
        when(action){
            ProfileAction.SignOut -> {
                signOut()
                resetState()
            }
            ProfileAction.SubmitGetAllFriends -> TODO()
        }
    }
}