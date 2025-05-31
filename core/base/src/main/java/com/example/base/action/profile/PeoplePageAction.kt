package com.example.base.action.profile

sealed class PeoplePageAction {
    data class SubmitPersonItem(val userId: String): PeoplePageAction()
    data object SubmitBackButton: PeoplePageAction()
}