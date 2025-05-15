package com.example.presentation.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.action.RegistrationAction
import com.example.base.event.RegistrationEvent
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
):ViewModel() {

    private val _state: MutableStateFlow<RegistrationState> = MutableStateFlow(RegistrationState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<RegistrationEvent>()
    val event: SharedFlow<RegistrationEvent> = _event


    fun updateState(param: RegisterUserParam){
        _state.value = RegistrationState(
            username = param.username,
            email = param.email,
            password = param.password,
            firstName = param.firstName,
            lastName = param.lastName
        )
    }

    private fun register() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            val result = runCatching {
                registerUseCase.invoke(RegisterUserParam(
                    email = _state.value.email,
                    username = _state.value.username,
                    password = _state.value.password,
                    confirmPassword = _state.value.password,
                    firstName = _state.value.firstName,
                    lastName = _state.value.lastName
                ))
            }

            _state.value = result.fold(
                onSuccess = { resultRegister ->
                    when(resultRegister.status) {
                        "success" -> {
                            _event.tryEmit(RegistrationEvent.NavigateToMapScreen)
                            RegistrationState(isSuccess = true)
                        }

                        else -> {RegistrationState(isSuccess = false)}
                    }
                },
                onFailure = {exception ->
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

    fun onAction(action: RegistrationAction){
        when(action){
            RegistrationAction.SubmitRegistration -> {
                register()
            }
        }
    }
}