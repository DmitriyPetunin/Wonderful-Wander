package com.example.feature.walk.api.state

import com.example.domain.model.user.People
import com.example.domain.model.walk.Point


data class CreateWalkState (
    val isLoading:Boolean = false,
    val queryParam: String = "",
    val listOfFriends:List<People> = emptyList(),
    val listOfResult:List<People> = emptyList(),

    val text: String = "Площадь Тукая",
    val point: Point = Point(latitude = 55.78874, longitude = 49.12214),
)