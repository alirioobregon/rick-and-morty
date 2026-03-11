package com.arkamo.rickandmorty.core.di

import com.arkamo.rickandmorty.domain.CharacterUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::CharacterUseCase)
}