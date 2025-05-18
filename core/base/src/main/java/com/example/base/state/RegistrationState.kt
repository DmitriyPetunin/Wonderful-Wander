package com.example.base.state

data class RegistrationState(

    val username: String = "",
    val password: String = "",
    val cpassword: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)