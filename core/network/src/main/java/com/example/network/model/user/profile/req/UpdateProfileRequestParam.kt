package com.example.network.model.user.profile.req



data class UpdateProfileRequestParam(
    val email:String,
    val firstname:String,
    val lastname:String,
    val bio:String,
    val photoVisibility: String,
    val walkVisibility: String
)