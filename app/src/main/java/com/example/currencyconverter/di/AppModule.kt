package com.example.currencyconverter.di

import RetrofitClient
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.local.CurrencyDatabase
import com.example.currencyconverter.data.local.dao.CurrencyDao
import com.example.currencyconverter.data.local.dao.ExchangeRateDao
import com.example.currencyconverter.data.local.dao.SettingsDao
import com.example.currencyconverter.data.remote.RemoteDataSource
import com.example.currencyconverter.data.repository.CurrencyRepository
import com.example.currencyconverter.data.repository.ExchangeRateRepository
import com.example.currencyconverter.data.repository.SettingsRepository
import com.example.currencyconverter.helpers.PreferencesHelper
import com.example.currencyconverter.presentation.navigation.NavigationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager {
        return NavigationManager()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CurrencyDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = CurrencyDatabase::class.java,
            name = "currency_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(database: CurrencyDatabase) = database.currencyDao()

    @Provides
    @Singleton
    fun provideExchangeRateDao(database: CurrencyDatabase) = database.exchangeRateDao()

    @Provides
    @Singleton
    fun provideSettingsDao(database: CurrencyDatabase) = database.settingsDao()

    @Provides
    @Singleton
    fun provideRemoteDataSource(): RemoteDataSource {
        return RemoteDataSource(RetrofitClient.api)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(currencyDao: CurrencyDao): CurrencyRepository {
        return CurrencyRepository(currencyDao)
    }

    @Provides
    @Singleton
    fun provideExchangeRateRepository(exchangeRateDao: ExchangeRateDao): ExchangeRateRepository {
        return ExchangeRateRepository(exchangeRateDao)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsDao: SettingsDao): SettingsRepository {
        return SettingsRepository(settingsDao)
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}