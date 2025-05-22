package com.example.base.event.register

sealed interface RegistrationEvent {
    data class ShowErrorMessage(val message:String): RegistrationEvent
    data object NavigateToMapScreen: RegistrationEvent
}