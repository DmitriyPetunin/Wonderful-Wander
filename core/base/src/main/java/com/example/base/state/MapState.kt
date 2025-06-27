package com.example.base.state

import com.example.base.model.walk.Point


data class MapState(
    val text: String = "Площадь Тукая",
    val point: Point = Point(latitude = 55.78874, longitude = 49.12214),
)