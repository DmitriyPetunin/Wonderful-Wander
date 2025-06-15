package com.example.base.util.validation

class EmailValidation {
    companion object{
        fun validateEmail(email: String): String? {
            return when {
                email.isBlank() -> "Email is required"
                else -> null
            }
        }
    }
}