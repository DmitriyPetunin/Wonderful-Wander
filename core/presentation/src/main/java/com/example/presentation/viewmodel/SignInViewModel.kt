package com.example.presentation.viewmodel


import android.content.Intent
import android.content.IntentSender
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.login.LoginAction
import com.example.base.event.login.LoginEvent
import com.example.base.model.user.login.LoginUserParam
import com.example.base.state.LoginState
import com.example.base.state.SignInResult
import com.example.base.util.validation.EmailValidation
import com.example.base.util.validation.PasswordValidation
import com.example.presentation.googleclient.GoogleAuthUiClient
import com.example.presentation.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val googleAuthUiClient: GoogleAuthUiClient,
) : ViewModel() {

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _signInIntentSender = MutableStateFlow<IntentSender?>(null)
    val signInIntentSender = _signInIntentSender.asStateFlow()

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event

    private fun signInWithIntent(intent: Intent) {
        viewModelScope.launch {
            val result = googleAuthUiClient.signInWithIntent(intent = intent)
            if (result.data != null) {
                _state.update {
                    it.copy(data = result.data)
                }
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
        _state.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val response = loginUseCase.invoke(
                LoginUserParam(
                    email = state.value.userName,
                    password = state.value.password
                )
            )
            response.fold(
                onSuccess = {
                    _event.emit(LoginEvent.SuccessLogin)
                },
                onFailure = { exception ->
                    exception.printStackTrace()
                },
            )
        }
        _state.update {
            it.copy(isLoading = false)
        }
        resetState()

    }

    private fun getSignedInUser() {
        _state.update {
            it.copy(data = googleAuthUiClient.getSignedInUser())
        }
    }


    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.SubmitLoginButton -> {
                login()
            }

            is LoginAction.UpdateUserNameField -> {
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

    private fun updateEmail(input: String) {

        val validation = EmailValidation.validateEmail(input)

        if (validation != null) {
            updateSupportingTextUserName(validation)
        } else updateSupportingTextUserName(null)

        _state.update { currentState ->
            val isEmailValid = currentState.supportingTextUserName == null
            val isPasswordValid = currentState.supportingTextPassword == null
            currentState.copy(
                userName = input,
                inputFieldsIsValid = isEmailValid && isPasswordValid
            )
        }
    }

    private fun updatePassword(input: String) {

        val validation = PasswordValidation.validatePassword(input)

        if (validation != null) {
            updateSupportingTextPassword(validation)
        } else updateSupportingTextPassword(null)

        _state.update { currentState ->
            val isEmailValid = currentState.supportingTextUserName == null
            val isPasswordValid = currentState.supportingTextPassword == null
            currentState.copy(
                password = input,
                inputFieldsIsValid = isEmailValid && isPasswordValid
            )
        }
    }

    private fun updateSupportingTextUserName(input: String?) {
        _state.update {
            it.copy(supportingTextUserName = input)
        }
    }

    private fun updateSupportingTextPassword(input: String?) {
        _state.update {
            it.copy(supportingTextPassword = input)
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
        viewModelScope.launch {
            _event.emit(LoginEvent.SuccessLogin)
        }
    }
}