package com.example.base.state


data class MapState(
    val text: String = "Площадь Тукая",
    val point: Point = Point(latitude = 55.78874, longitude = 49.12214),
)

data class Point(
    val latitude: Double,
    val longitude: Double,
)