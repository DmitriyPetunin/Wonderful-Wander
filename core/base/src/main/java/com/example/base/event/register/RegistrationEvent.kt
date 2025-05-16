package com.example.base.event.register

sealed class RegistrationEvent {
    data class ShowErrorMessage(val message:String) : RegistrationEvent()
    data object NavigateToMapScreen: RegistrationEvent()
}