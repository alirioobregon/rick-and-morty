package com.arkamo.rickandmorty.core.di

import com.arkamo.rickandmorty.core.network.createHttpClient
import com.arkamo.rickandmorty.core.network.httpClientEngine
import com.arkamo.rickandmorty.data.remote.RickAndMortyApi
import com.arkamo.rickandmorty.data.repository.CharacterRepositoryImpl
import com.arkamo.rickandmorty.domain.repository.CharacterRepository
import com.arkamo.rickandmorty.presentation.main.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    // Network
    single { createHttpClient(httpClientEngine()) }
    singleOf(::RickAndMortyApi)

    // Repositories
    singleOf(::CharacterRepositoryImpl) bind CharacterRepository::class

    // ViewModels
    viewModelOf(::MainViewModel)
}
