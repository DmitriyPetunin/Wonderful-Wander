package com.example.base.state

import com.example.base.model.user.People

data class CreateWalkState (
    val queryParam: String = "",
    val listOfFriends:List<People> = listOf(
        People(username = "Ваня", userId = "", avatarUrl = "https://edgio.clien.net/F01/15434844/65337895414904.JPG"),
        People(username = "Егор", userId = "", avatarUrl = "https://cdn.greenpostkorea.co.kr/news/photo/202505/301815_302257_1013.png"),
        People(username = "Дима", userId = "", avatarUrl = "http://www.coinreaders.com/data/coinreaders_com/banner/favicon.ico")

    )
)