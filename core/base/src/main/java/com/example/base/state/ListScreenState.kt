package com.example.base.state

data class ListScreenState<T> (
    val listOfPeople: List<T> = emptyList()
)