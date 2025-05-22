package com.example.base.util.validation

class EmailValidation {
    companion object{
        fun validateEmail(email: String): String? {
            return when {
                email.isBlank() -> "Email is required"
                !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
                else -> null
            }
        }
    }
}