package com.example.domain.model.walk


data class WalkCreateParam (
    val name: String,
    val listOfParticipants: List<String>,
    val startPoint: Point,
)