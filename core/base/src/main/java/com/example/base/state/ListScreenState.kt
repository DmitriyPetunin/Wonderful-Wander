package com.example.base.state

import com.example.base.model.user.People

data class ListScreenState (
    val people: String = "",
    val listOfPeople: List<People> = emptyList()
)