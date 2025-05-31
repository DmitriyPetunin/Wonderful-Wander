package com.example.base.event.people

sealed interface PeoplePageEvent {
    data class NavigateToPersonWithUserId(val id:String): PeoplePageEvent
}