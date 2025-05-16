package com.example.base.action.register

sealed class RegistrationAction{
    data object SubmitRegistration : RegistrationAction()
}