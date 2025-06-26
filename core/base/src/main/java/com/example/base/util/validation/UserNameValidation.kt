package com.example.base.util.validation

class UserNameValidation {
    companion object{
        fun validateUsername(userName: String): String? {
            return when {
                userName.isBlank() -> "UserName is required"
                else -> null
            }
        }
    }
}