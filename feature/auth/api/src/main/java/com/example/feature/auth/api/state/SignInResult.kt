package com.example.feature.auth.api.state

import com.example.base.state.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)