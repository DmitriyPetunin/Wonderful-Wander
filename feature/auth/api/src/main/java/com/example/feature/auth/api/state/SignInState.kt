package com.example.feature.auth.api.state

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)