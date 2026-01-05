package com.example.feature.auth.api.event.login

sealed interface LoginEvent {
    data object UserExist: LoginEvent
    data object ErrorLogin: LoginEvent
    data object SuccessLogin: LoginEvent
}