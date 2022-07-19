package com.clearscore.test.di

import com.clearscore.test.BuildConfig
import com.clearscore.data.dataSource.FetchScoreDataSource
import com.clearscore.data.repository.ScoreDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object PresentationModule {
//
////    @Provides
////    fun provideDataSource(getScoreApi: com.clearscore.data.api.GetScoreApi): FetchScoreDataSource =
////        FetchScoreDataSourceImpl(getScoreApi)
////
////    @Provides
////    fun provideRepository(dataSource: com.clearscore.data.dataSource.FetchScoreDataSource): ScoreDataRepository =
////        ScoreDataRepositoryImpl(dataSource)
//
//}