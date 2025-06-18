package com.example.base.state

import com.example.base.model.user.People

data class ListScreenState (
    val people: PeopleEnum = PeopleEnum.FRIENDS,
    val listOfPeople: List<People> = emptyList(),
    val currentPage: Int = 1,
    val limit:Int = 10,

    val isLoading:Boolean = true,
    val endReached:Boolean = false,
    val isInitialLoading:Boolean = true
)

enum class PeopleEnum{
    FRIENDS {
        override fun toString() = "Друзья"
    },
    FOLLOWERS {
        override fun toString() = "Подписчики"
    },
    FOLLOWING {
        override fun toString() = "Подписки"
    };

    companion object{
        fun fromString(value:String):PeopleEnum {
            return entries.find { it.toString() == value } ?: FRIENDS
        }
    }

}
