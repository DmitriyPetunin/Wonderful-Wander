package com.example.base.state

data class LoginState (
    val email: String = "",
    val password: String = "",

    val supportingTextEmail: String? = "",
    val supportingTextPassword: String? = "",

    val inputFieldsIsValid: Boolean = false,

    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,


    val data: UserData? = null,
    val errorMessage: String? = null
)