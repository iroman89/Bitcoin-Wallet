package com.beetrack.bitcoinwallet.presentation.di.module

import com.beetrack.bitcoinwallet.data.repository.BlockCypherRepositoryImpl
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repository: BlockCypherRepositoryImpl): BlockCypherRepository
}