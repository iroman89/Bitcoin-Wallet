package com.beetrack.bitcoinwallet.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.beetrack.bitcoinwallet.data.local.BlockCypherDatabase
import com.beetrack.bitcoinwallet.data.local.DatabaseConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context) =
        Room.databaseBuilder(
            context,
            BlockCypherDatabase::class.java,
            DatabaseConstants.DATABASE_NAME
        ).allowMainThreadQueries().build()
}