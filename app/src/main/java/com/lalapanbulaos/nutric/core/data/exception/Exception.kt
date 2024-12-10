package com.lalapanbulaos.nutric.core.data.exception

sealed class RepositoryException(message: String) : Exception(message)
class AuthenticationException(message: String) : RepositoryException(message)
class ApiException(val code: Int, message: String) : RepositoryException(message)
class UnexpectedException(cause: Throwable) : RepositoryException(cause.message ?: "Unexpected error")