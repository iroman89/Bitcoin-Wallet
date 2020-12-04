package cl.iroman.bitcoinwallet.presentation.di.module

import cl.iroman.bitcoinwallet.data.local.LocalDataSourceImpl
import cl.iroman.bitcoinwallet.data.remote.RemoteDataSourceImpl
import cl.iroman.bitcoinwallet.data.repository.BlockCypherRepositoryImpl
import cl.iroman.bitcoinwallet.data.repository.source.LocalDataSource
import cl.iroman.bitcoinwallet.data.repository.source.RemoteDataSource
import cl.iroman.bitcoinwallet.domain.repository.BlockCypherRepository
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