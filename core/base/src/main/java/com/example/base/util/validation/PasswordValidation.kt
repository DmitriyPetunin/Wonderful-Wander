package com.example.base.util.validation

class PasswordValidation {
    companion object{
        fun validatePassword(password: String): String? {
            return when {
                password.length < 8 -> "Password must be at least 8 characters"
                !password.any { it.isDigit() } -> "Password must contain at least one digit"
                !password.any { it.isLetter() } -> "Password must contain at least one letter"
                else -> null
            }
        }
    }
}