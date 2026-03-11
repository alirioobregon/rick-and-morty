package com.arkamo.rickandmorty.core.di

import com.arkamo.rickandmorty.Greeting
import com.arkamo.rickandmorty.presentation.main.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    factory { Greeting() }
    viewModelOf(::MainViewModel)
}
