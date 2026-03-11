package com.arkamo.rickandmorty.core.di

import com.arkamo.rickandmorty.core.network.createHttpClient
import com.arkamo.rickandmorty.core.network.httpClientEngine
import com.arkamo.rickandmorty.data.remote.RickAndMortyApi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
    single { createHttpClient(httpClientEngine()) }
    singleOf(::RickAndMortyApi)
}