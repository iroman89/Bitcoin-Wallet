package com.beetrack.bitcoinwallet.presentation.di.module

import com.beetrack.bitcoinwallet.data.local.LocalDataSourceImpl
import com.beetrack.bitcoinwallet.data.remote.RemoteDataSourceImpl
import com.beetrack.bitcoinwallet.data.repository.BlockCypherRepositoryImpl
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import com.beetrack.bitcoinwallet.data.repository.source.RemoteDataSource
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRepository(repository: BlockCypherRepositoryImpl): BlockCypherRepository

    @Binds
    abstract fun bindRemoteDataSource(dataSource: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindLocalDataSource(dataSource: LocalDataSourceImpl): LocalDataSource
}