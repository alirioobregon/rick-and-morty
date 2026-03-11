package com.arkamo.rickandmorty.core.di

import com.arkamo.rickandmorty.data.repository.CharacterRepositoryImpl
import com.arkamo.rickandmorty.domain.repository.CharacterRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoriesModule = module {
    singleOf(::CharacterRepositoryImpl) bind CharacterRepository::class
}