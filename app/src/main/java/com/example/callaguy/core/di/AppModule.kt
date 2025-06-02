package com.example.callaguy.core.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.callaguy.core.constants.Constants
import com.example.callaguy.core.serialization.BigDecimalSerializer
import com.example.callaguy.core.serialization.LocalDateSerializer
import com.example.callaguy.core.serialization.LocalDateTimeSerializer
import com.example.callaguy.core.serialization.LocalTimeSerializer
import com.example.callaguy.data.auth.AuthInterceptor
import com.example.callaguy.data.auth.SharedPrefTokenProvider
import com.example.callaguy.data.auth.TokenProvider
import com.example.callaguy.data.local.ProfileDao
import com.example.callaguy.data.local.ProfileDatabase
import com.example.callaguy.data.remote.ApiService
import com.example.callaguy.data.repository.AuthRepositoryImpl
import com.example.callaguy.data.repository.GetServiceRequestRepositoryImpl
import com.example.callaguy.data.repository.ProfileRepositoryImpl
import com.example.callaguy.data.repository.ServiceRepositoryImpl
import com.example.callaguy.data.repository.ServiceRequestRepositoryImpl
import com.example.callaguy.data.repository.SubServiceRepositoryImpl
import com.example.callaguy.domain.repository.AuthRepository
import com.example.callaguy.domain.repository.GetServiceRequestRepository
import com.example.callaguy.domain.repository.ProfileRepository
import com.example.callaguy.domain.repository.ServiceRepository
import com.example.callaguy.domain.repository.ServiceRequestRepository
import com.example.callaguy.domain.repository.SubServiceRepository
import com.example.callaguy.domain.usecase.AuthUserUseCase
import com.example.callaguy.domain.usecase.GetServiceRequestUseCase
import com.example.callaguy.domain.usecase.ProfileUseCase
import com.example.callaguy.domain.usecase.ServiceRequestUseCase
import com.example.callaguy.domain.usecase.ServiceUseCases
import com.example.callaguy.domain.usecase.SubServiceUseCase
import com.example.callaguy.domain.validation.ValidateEmail
import com.example.callaguy.domain.validation.ValidatePassword
import com.example.callaguy.domain.validation.ValidateRepeatedPassword
import com.example.callaguy.domain.validation.ValidateServiceRequestForm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenProvider(sharedPreferences: SharedPreferences) : TokenProvider {
        return SharedPrefTokenProvider(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenProvider: TokenProvider) : AuthInterceptor {
        return AuthInterceptor(tokenProvider)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: AuthInterceptor) : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient) : Retrofit {
        val json = Json {
            serializersModule = SerializersModule {
                contextual(LocalDate::class, LocalDateSerializer)
                contextual(LocalTime::class, LocalTimeSerializer)
                contextual(BigDecimal::class , BigDecimalSerializer)
                contextual(LocalDateTime::class , LocalDateTimeSerializer)
            }
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun ProvideAuthApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : ProfileDatabase {
        return Room.databaseBuilder(
            context ,
            ProfileDatabase::class.java ,
            "profile_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProfileDao(database: ProfileDatabase) : ProfileDao {
        return database.dao()
    }

    @Provides
    fun provideOrderRequestRepository(apiService: ApiService) : GetServiceRequestRepository {
        return GetServiceRequestRepositoryImpl(apiService)
    }

    @Provides
    fun provideOrderRequestUseCase(getServiceRequestRepository: GetServiceRequestRepository) : GetServiceRequestUseCase {
        return GetServiceRequestUseCase(getServiceRequestRepository)
    }

    @Provides
    fun provideServiceRequestFormValidation() : ValidateServiceRequestForm {
        return ValidateServiceRequestForm()
    }

    @Provides
    @Singleton
    fun provideSubServiceRequestRepository(apiService: ApiService) : ServiceRequestRepository {
        return ServiceRequestRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideSubServiceRequestUseCase(serviceRequestRepository: ServiceRequestRepository) : ServiceRequestUseCase {
        return ServiceRequestUseCase(serviceRequestRepository)
    }

    @Provides
    @Singleton
    fun provideSubServiceRepository(apiService: ApiService) : SubServiceRepository {
        return SubServiceRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun provideSubServiceUseCase(subServiceRepository: SubServiceRepository) : SubServiceUseCase {
        return SubServiceUseCase(subServiceRepository)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(apiService: ApiService , dao : ProfileDao) : ProfileRepository {
        return ProfileRepositoryImpl( dao , apiService)
    }
    @Provides
    @Singleton
    fun provideProfileUseCase(profileRepository: ProfileRepository) : ProfileUseCase {
        return ProfileUseCase(profileRepository)
    }

    @Provides
    @Singleton
    fun provideServiceRepository(apiService: ApiService) : ServiceRepository {
        return ServiceRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideServiceUseCase(serviceRepository: ServiceRepository) : ServiceUseCases {
        return ServiceUseCases(serviceRepository)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: ApiService) : AuthRepository {
        return AuthRepositoryImpl(authApiService)
    }

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository , prefs : SharedPreferences) : AuthUserUseCase {
        return AuthUserUseCase(authRepository , prefs)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext app : Context) : SharedPreferences {
        return app.getSharedPreferences("prefs" , MODE_PRIVATE )
    }

    @Provides
    @Singleton
    fun provideEmailValidationUseCase() : ValidateEmail {
        return ValidateEmail()
    }

    @Provides
    @Singleton
    fun providePasswordValidationUseCase() : ValidatePassword {
        return ValidatePassword()
    }

    @Provides
    @Singleton
    fun provideRepeatPasswordValidationUseCase() : ValidateRepeatedPassword {
        return ValidateRepeatedPassword()
    }
}