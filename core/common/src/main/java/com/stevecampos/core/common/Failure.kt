package com.stevecampos.core.common

sealed class Failure {
    data class BadRequest(val any: Any? = null) : Failure()
    data class AuthorizationRequired(val any: Any? = null) : Failure()
    data class AccessDeniedOrForbidden(val any: Any? = null) : Failure()
    data class NotFound(val any: Any? = null) : Failure()
    data class Conflict(val any: Any? = null) : Failure()
    data class DestinationLocked(val any: Any? = null) : Failure()
    data class Unprocessable(val any: Any? = null) : Failure()
    data class Locked(val any: Any? = null) : Failure()
    data class TooManyRequest(val any: Any? = null) : Failure()
    data class Internal(val any: Any? = null) : Failure()
    data class ServiceUnavailable(val any: Any? = null) : Failure()
    data class PreconditionFailed(val any: Any? = null) : Failure()
    object NetworkLost : Failure()
    object Others : Failure()

    companion object {
        fun findFailure(httpCode: Int): Failure {
            return when (httpCode) {
                400 -> BadRequest()
                401 -> AuthorizationRequired()
                403 -> AccessDeniedOrForbidden()
                404 -> NotFound()
                409 -> Conflict()
                412 -> PreconditionFailed()
                421 -> DestinationLocked()
                422 -> Unprocessable()
                423 -> Locked()
                429 -> TooManyRequest()
                500 -> Internal()
                503 -> ServiceUnavailable()
                else -> Others
            }
        }
    }
}

