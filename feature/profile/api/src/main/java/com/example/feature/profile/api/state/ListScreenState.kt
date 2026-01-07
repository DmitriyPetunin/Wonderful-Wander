package com.example.feature.profile.api.state

import com.example.base.enums.PeopleEnum
import com.example.domain.model.user.People

data class ListScreenState (
    val people: PeopleEnum = PeopleEnum.FRIENDS,
    val listOfPeople: List<People> = emptyList(),
    val currentPage: Int = 1,
    val limit:Int = 10,

    val isLoading:Boolean = true,
    val endReached:Boolean = false,
    val isInitialLoading:Boolean = true
)
