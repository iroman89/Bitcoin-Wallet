package com.beetrack.bitcoinwallet.presentation.di.module

import android.content.Context
import android.util.Log
import androidx.work.*
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import com.beetrack.bitcoinwallet.data.repository.source.RemoteDataSource
import com.beetrack.bitcoinwallet.data.worker.AddressBalanceWorker
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class WorkerModule {

    @Provides
    @Singleton
    fun provideAddressBalanceWorker(delegateFactory: AddressBalanceWorkerFactoryDelegate): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(delegateFactory)
            .build()
    }

    @Singleton
    class AddressBalanceWorkerFactoryDelegate @Inject constructor(
        localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource,
    ) : DelegatingWorkerFactory() {
        init {
            addFactory(AddressBalanceWorkerFactory(localDataSource, remoteDataSource))
        }
    }

    class AddressBalanceWorkerFactory(
        private val localDataSource: LocalDataSource,
        private val remoteDataSource: RemoteDataSource,
    ) : WorkerFactory() {

        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters,
        ): ListenableWorker? =
            when (workerClassName) {
                AddressBalanceWorker::class.java.name ->
                    AddressBalanceWorker(appContext,
                        workerParameters,
                        localDataSource,
                        remoteDataSource)
                else -> null
            }
    }
}