package com.example.lockily.di

import android.content.Context
import androidx.room.Room
import com.example.lockily.data.local.LockilyDatabase
import com.example.lockily.data.repository.LockilyRepoImpl
import com.example.lockily.domain.repository.LockilyRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, LockilyDatabase::class.java, "lockily_db")
        .build()

    @Singleton
    @Provides
    fun provideRepository(
        lockilyDatabase: LockilyDatabase
    ): LockilyRepo {
        return LockilyRepoImpl(lockilyDatabase)
    }
}