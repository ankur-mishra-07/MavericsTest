package com.test.mavericstest.di.modules

import com.test.mavericstest.data.services.MovieDataService
import com.test.mavericstest.di.annotations.ApplicationScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Provides network services for data
 */
@Module(includes = [NetworkModule::class])
class NetworkServiceModule {

    @Provides
    @ApplicationScope
    fun provideApiService(retrofit: Retrofit): MovieDataService {
        return retrofit.create(MovieDataService::class.java)
    }
}