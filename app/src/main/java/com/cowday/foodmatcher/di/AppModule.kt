package com.cowday.foodmatcher.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cowday.foodmatcher.database.BeerDatabase
import com.cowday.foodmatcher.network.PunkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(PunkApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): PunkApi{
        return retrofit.create(PunkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): BeerDatabase{
        return Room.databaseBuilder(app, BeerDatabase::class.java, "beer_database")
            .build()
    }

}