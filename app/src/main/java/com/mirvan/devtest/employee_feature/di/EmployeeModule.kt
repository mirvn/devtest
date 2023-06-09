package com.mirvan.devtest.employee_feature.di

import com.mirvan.devtest.BuildConfig
import com.mirvan.devtest.employee_feature.data.remote.EmployeeApi
import com.mirvan.devtest.employee_feature.data.repositoryImpl.AddEmployeeRepositoryImpl
import com.mirvan.devtest.employee_feature.data.repositoryImpl.DeleteEmployeeRepositoryImpl
import com.mirvan.devtest.employee_feature.data.repositoryImpl.GetAllEmployeeRepositoryImpl
import com.mirvan.devtest.employee_feature.data.repositoryImpl.UpdateEmployeeRepositoryImpl
import com.mirvan.devtest.employee_feature.domain.repository.AddEmployeeRepository
import com.mirvan.devtest.employee_feature.domain.repository.DeleteEmployeeRepository
import com.mirvan.devtest.employee_feature.domain.repository.GetAllEmployeeRepository
import com.mirvan.devtest.employee_feature.domain.repository.UpdateEmployeeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmployeeModule {

    @Provides
    @Singleton
    fun provideDeleteEmployeeRepository(
        api: EmployeeApi
    ): DeleteEmployeeRepository {
        return DeleteEmployeeRepositoryImpl(
            api = api
        )
    }

    @Provides
    @Singleton
    fun provideUpdateEmployeeRepository(
        api: EmployeeApi
    ): UpdateEmployeeRepository {
        return UpdateEmployeeRepositoryImpl(
            api = api
        )
    }

    @Provides
    @Singleton
    fun provideAddEmployeeRepository(
        api: EmployeeApi
    ): AddEmployeeRepository {
        return AddEmployeeRepositoryImpl(
            api = api
        )
    }

    @Provides
    @Singleton
    fun provideGetAllEmployeeRepository(
        api: EmployeeApi
    ): GetAllEmployeeRepository {
        return GetAllEmployeeRepositoryImpl(
            api = api
        )
    }

    @Provides
    @Singleton
    fun provideEmployeeApi(): EmployeeApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_API)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeeApi::class.java)
    }
}
