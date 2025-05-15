package com.example.base.event

sealed class RegistrationEvent {
    data class ShowErrorMessage(val message:String) :RegistrationEvent()
    data object NavigateToMapScreen: RegistrationEvent()
}