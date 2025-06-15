package com.example.base.state

data class LoginState (
    val userName: String = "john_doe",
    val password: String = "3333F333333",

    val supportingTextUserName: String? = "",
    val supportingTextPassword: String? = "",

    val inputFieldsIsValid: Boolean = false,

    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,

    val isLoading:Boolean = false,


    val data: UserData? = null,
    val errorMessage: String? = null
)