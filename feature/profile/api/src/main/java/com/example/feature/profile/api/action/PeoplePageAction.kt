package com.example.feature.profile.api.action

import com.example.base.enums.PeopleEnum


sealed class PeoplePageAction {

    data class UpdatePeopleState(val input:PeopleEnum):PeoplePageAction()
    data class SubmitPersonItem(val userId: String): PeoplePageAction()
    data object SubmitBackButton: PeoplePageAction()

    data object LoadMore:PeoplePageAction()

}