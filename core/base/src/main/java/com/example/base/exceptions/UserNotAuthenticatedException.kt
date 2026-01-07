package com.example.base.exceptions

class UserNotAuthenticatedException(message:String = "вы не вошли в аккаунт"):RuntimeException(message)