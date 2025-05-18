package com.example.presentation.viewmodel


import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.LoginAction
import com.example.base.action.profile.ProfileAction
import com.example.base.event.LoginEvent
import com.example.base.event.profile.ProfileEvent
import com.example.base.state.LoginState
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

    private val _state:MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()


    private val _signInIntentSender = MutableStateFlow<IntentSender?>(null)
    val signInIntentSender = _signInIntentSender.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event

    private fun signInWithIntent(intent: Intent) {
        viewModelScope.launch {
            val result = googleAuthUiClient.signInWithIntent(intent = intent)
            if (result.data != null) {
                _signInResult.value = result
                updateSignInResult(result)
            }
        }
    }


    private fun signIn() {
        viewModelScope.launch {
            _signInIntentSender.value = googleAuthUiClient.signIn()
        }
    }

    private fun login() {


        TODO("валидация")

        //useCase
        resetState()

    }

    private fun getSignedInUser() {
        _state.update {
            it.copy(data = googleAuthUiClient.getSignedInUser())
        }
    }


    fun onAction(action: LoginAction){
        when(action){
            is LoginAction.SubmitLoginButton -> {
                login()
            }
            is LoginAction.UpdateEmailField -> {
                updateEmail(action.input)
            }
            is LoginAction.UpdatePasswordField -> {
                updatePassword(action.input)
            }

            is LoginAction.SignInWithIntent -> {
                signInWithIntent(action.intent)
            }

            is LoginAction.SignInWithGoogle -> {
                signIn()
            }
        }

    }

    private fun updateEmail(input:String){
        _state.update {
            it.copy(email = input)
        }
    }
    private fun updatePassword(input:String){
        _state.update {
            it.copy(password = input)
        }
    }
    private fun resetState() {
        _state.update { LoginState() }
    }
    private fun updateSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
    }
}