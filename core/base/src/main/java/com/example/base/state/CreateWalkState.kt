package com.example.base.state

import com.example.base.model.user.People

data class CreateWalkState (
    val isLoading:Boolean = false,
    val queryParam: String = "",
    val listOfFriends:List<People> = mutableListOf()
)