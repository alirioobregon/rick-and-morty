package com.arkamo.rickandmorty.presentation.main

data class MainUiState(
    val tagline: String = "Wubba Lubba Dub Dub!",
    val isReady: Boolean = false,
    val title: String = "Rick & Morty"
)
