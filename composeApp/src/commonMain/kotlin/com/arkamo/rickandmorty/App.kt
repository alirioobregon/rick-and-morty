package com.arkamo.rickandmorty

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkamo.rickandmorty.core.di.appModule
import com.arkamo.rickandmorty.presentation.main.MainScreen
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = { modules(appModule) }) {
        MainScreen()
    }
}
