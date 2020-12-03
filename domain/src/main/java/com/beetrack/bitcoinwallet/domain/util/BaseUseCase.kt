package com.beetrack.bitcoinwallet.domain.util

abstract class BaseUseCase<Response, Params> {
    abstract suspend fun invoke(params: Params): Response
}