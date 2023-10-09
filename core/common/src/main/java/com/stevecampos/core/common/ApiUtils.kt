package com.stevecampos.core.common

import retrofit2.Response

suspend fun <R> executeApiService(
    api: suspend () -> Response<R>
): Result<R> {
    return executeApiService(api = api, transformInfrastructureToDomain = { it })
}

fun <R, D> transform(response: Response<R>): Result<D> {
    val httpCode: Int = response.code()
    return try {
        Result.Error(Failure.findFailure(httpCode))
    } catch (t: Throwable) {
        Result.Error(Failure.Others)
    }
}

suspend fun <D, R> executeApiService(
    transformInfrastructureToDomain: (R) -> D,
    api: suspend () -> Response<R>
): Result<D> {
    try {
        val response: Response<R> = api()
        if (response.isSuccessful) {
            val data = response.body()!!
            val domainResponse = transformInfrastructureToDomain.invoke(data)
            return Result.Success(domainResponse)
        }
        return transform(response)
    } catch (t: Throwable) {
        return Result.Error(Failure.Others)
    }
}