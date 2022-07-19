package com.clearscore.data.di

import com.clearscore.data.api.GetScoreApi
import com.clearscore.data.config.Endpoints.BASE_URL
import com.clearscore.data.dataSource.FetchScoreDataSource
import com.clearscore.data.dataSource.FetchScoreDataSourceImpl
import com.clearscore.data.repository.ScoreDataRepository
import com.clearscore.data.repository.ScoreDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GetScoreApi =
        retrofit.create(GetScoreApi::class.java)

    @Provides
    fun provideDataSource(getScoreApi: GetScoreApi): FetchScoreDataSource =
        FetchScoreDataSourceImpl(getScoreApi)

    @Provides
    fun provideRepository(dataSource: FetchScoreDataSource): ScoreDataRepository =
        ScoreDataRepositoryImpl(dataSource)

}