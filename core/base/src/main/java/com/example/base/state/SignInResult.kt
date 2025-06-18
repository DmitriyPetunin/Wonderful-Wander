package com.example.base.state

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)