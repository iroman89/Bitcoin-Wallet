package com.beetrack.bitcoinwallet.data.worker

import android.content.Context
import androidx.work.*
import com.beetrack.bitcoinwallet.data.mapper.toEntity
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import com.beetrack.bitcoinwallet.data.repository.source.RemoteDataSource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.single
import java.util.concurrent.TimeUnit

class AddressBalanceWorker constructor(
    context: Context,
    params: WorkerParameters,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : CoroutineWorker(context, params) {

    /**
     * Gets address, if has not skip process else gets address balanc and insert into DB.
     * @exception Nothing
     */
    override suspend fun doWork(): Result {
        try {
            localDataSource.getAddress()
                .catch { /*Nothing*/ }
                .single().first().also {
                    remoteDataSource.getAddressBalance(it.address).toEntity()
                        .apply {
                            localDataSource.insertAddressBalance(this)
                            return Result.retry()
                        }
                }
        } catch (e: Throwable) {
            return Result.retry()
        }
    }
}

val AddressBalanceWorkRequest = OneTimeWorkRequestBuilder<AddressBalanceWorker>()
    .setInitialDelay(3, TimeUnit.MINUTES)
    .setConstraints(
        Constraints.Builder()
            .apply {
                setRequiredNetworkType(NetworkType.CONNECTED)
                setRequiresBatteryNotLow(true)
                setRequiresStorageNotLow(true)
            }.build()
    )
    .setBackoffCriteria(
        BackoffPolicy.LINEAR,
        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
        TimeUnit.MILLISECONDS
    )
    .build()