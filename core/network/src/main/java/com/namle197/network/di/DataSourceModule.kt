package com.namle197.network.di

import com.namle197.network.MobileTakeHomeDataSource
import com.namle197.network.retrofit.RetrofitMobileTakeHomeNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun binds(retrofitMobileTakeHomeNetwork: RetrofitMobileTakeHomeNetwork): MobileTakeHomeDataSource
}
