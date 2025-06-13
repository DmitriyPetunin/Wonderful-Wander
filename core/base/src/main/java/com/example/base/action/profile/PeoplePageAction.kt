package com.example.base.action.profile

sealed class PeoplePageAction {
    data class SubmitPersonItem(val userInfo: String): PeoplePageAction()
    data object SubmitBackButton: PeoplePageAction()
}