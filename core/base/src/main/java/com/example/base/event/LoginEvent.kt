package com.example.base.event

sealed interface LoginEvent {
    data object UserExist: LoginEvent
    data object ErrorLogin: LoginEvent
    data object SuccessLogin: LoginEvent
}