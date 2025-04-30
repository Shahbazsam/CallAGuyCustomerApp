package com.example.callaguy.domain.model

sealed class ResultClass<T>(val data : T? = null) {
    class Authorized<T>(data : T? = null) : ResultClass<T>(data)
    class Unauthorized<T> : ResultClass<T>()
    class UnKnownError<T> : ResultClass<T>()
}