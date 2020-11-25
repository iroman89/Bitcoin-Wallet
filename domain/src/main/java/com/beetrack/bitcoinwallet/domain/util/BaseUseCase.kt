package com.beetrack.bitcoinwallet.domain.util

abstract class BaseUseCase<Response, Request> {
    abstract suspend fun invoke(request: Request): Response
}