package com.example.feature.profile.api.event

sealed interface PeoplePageEvent {
    data class NavigateToPersonProfileWithUserId(val userId:String): PeoplePageEvent
}