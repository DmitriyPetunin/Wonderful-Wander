package com.example.base.action.register

sealed class RegistrationAction{

    data object SubmitRegistration : RegistrationAction()

    data class UpdateEmailField(val input:String): RegistrationAction()
    data class UpdateUserNameField(val input:String): RegistrationAction()
    data class UpdatePasswordField(val input:String): RegistrationAction()
    data class UpdateCPasswordField(val input:String): RegistrationAction()
    data class UpdateFirstNameField(val input:String): RegistrationAction()
    data class UpdateLastNameField(val input:String): RegistrationAction()
}