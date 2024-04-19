package com.slowerror.rickandmorty.domain.common

sealed class CustomException : Exception() {
    data object IncorrectRequest : CustomException()
    data class HttpException(override val message: String? = null) : CustomException()
    data class OtherException(override val message: String? = null) : CustomException()
}