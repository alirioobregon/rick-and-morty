package com.arkamo.rickandmorty.core.di

import com.arkamo.rickandmorty.presentation.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
}