package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.register.RegistrationAction
import com.example.base.event.register.RegistrationEvent
import com.example.base.model.user.RegisterUserParam
import com.example.base.state.RegistrationState
import com.example.presentation.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<RegistrationEvent>()
    val event: SharedFlow<RegistrationEvent> = _event


    fun onAction(action: RegistrationAction) {
        when (action) {
            RegistrationAction.SubmitRegistration -> {
                register()
            }

            is RegistrationAction.UpdateCPasswordField -> {
                updateCPasswordState(action.input)
            }

            is RegistrationAction.UpdateEmailField -> {
                updateEmailState(action.input)
            }
            is RegistrationAction.UpdateFirstNameField -> {
                updateFirstNameState(action.input)
            }
            is RegistrationAction.UpdateLastNameField -> {
                updateLastNameState(action.input)
            }
            is RegistrationAction.UpdatePasswordField -> {
                updatePasswordState(action.input)
            }
            is RegistrationAction.UpdateUserNameField -> {
                updateUserNameState(action.input)
            }
        }
    }

    private fun register() {
        _state.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val result = registerUseCase.invoke(
                    RegisterUserParam(
                        email = state.value.email,
                        username = state.value.username,
                        password = state.value.password,
                        confirmPassword = state.value.password,
                        firstName = state.value.firstName,
                        lastName = state.value.lastName
                    )
                )

            _state.value = result.fold(
                onSuccess = { resultRegister ->
                    when (resultRegister.status) {
                        "success" -> {
                            _event.emit(RegistrationEvent.NavigateToMapScreen)
                            RegistrationState(isSuccess = true)
                        }

                        else -> {
                            RegistrationState(isSuccess = false)
                        }
                    }
                },
                onFailure = { exception ->
                    when (exception) {

                        is IOException -> {
                            _event.emit(RegistrationEvent.ShowErrorMessage("Registration failed"))
                            Log.d("TEST-TAG", "error IO")
                            RegistrationState(errorMessage = "IO error")
                        }

                        else -> {
                            _event.emit(RegistrationEvent.ShowErrorMessage("Registration failed"))
                            exception.message?.let { Log.d("TEST-TAG", it) }
                            RegistrationState(errorMessage = "unknown error")
                        }
                    }
                }
            )
        }
    }

    private fun updateEmailState(input: String) {
        _state.update {
            it.copy(email = input)
        }
    }

    private fun updatePasswordState(input: String) {
        _state.update {
            it.copy(password = input)
        }
    }

    private fun updateCPasswordState(input: String) {
        _state.update {
            it.copy(cpassword = input)
        }
    }

    private fun updateUserNameState(input: String) {
        _state.update {
            it.copy(username = input)
        }
    }

    private fun updateFirstNameState(input: String) {
        _state.update {
            it.copy(firstName = input)
        }
    }

    private fun updateLastNameState(input: String) {
        _state.update {
            it.copy(lastName = input)
        }
    }
}