package com.example.base.action.profile

import com.example.base.state.PeopleEnum

sealed class PeoplePageAction {

    data class UpdatePeopleState(val input:PeopleEnum):PeoplePageAction()
    data class SubmitPersonItem(val userInfo: String): PeoplePageAction()
    data object SubmitBackButton: PeoplePageAction()

    data object LoadMore:PeoplePageAction()

}