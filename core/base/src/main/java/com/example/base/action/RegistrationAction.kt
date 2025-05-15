package com.example.base.action

sealed class RegistrationAction{
    data object SubmitRegistration : RegistrationAction()
}