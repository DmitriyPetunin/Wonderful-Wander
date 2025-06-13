package com.example.base.event.people

sealed interface PeoplePageEvent {
    data class NavigateToPersonProfileWithUserId(val userInfo:String): PeoplePageEvent
}