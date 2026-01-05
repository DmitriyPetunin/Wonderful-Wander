package com.example.base.enums

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
        fun fromString(value:String): PeopleEnum {
            return entries.find { it.toString() == value } ?: FRIENDS
        }
    }

}