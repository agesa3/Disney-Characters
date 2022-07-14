package com.agesadev.disney.di

import com.agesadev.disney.data.remote.repository.CharacterRepositoryImpl
import com.agesadev.disney.data.remote.retrofit.CharacterApi
import com.agesadev.disney.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideCharacterRepository(characterApi: CharacterApi): CharacterRepository {
        return CharacterRepositoryImpl(characterApi)
    }
}