package com.beetrack.bitcoinwallet.presentation.di.module

import com.beetrack.bitcointwallet.presentation.BuildConfig
import com.beetrack.bitcoinwallet.data.network.BlockCypherAPI
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }

        clientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(10, TimeUnit.SECONDS)
        clientBuilder.readTimeout(10, TimeUnit.SECONDS)

        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        return retrofitBuilder.build()
    }

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): BlockCypherAPI {
        return retrofit.create(BlockCypherAPI::class.java)
    }
}