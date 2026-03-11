package com.arkamo.rickandmorty.presentation.main

import com.arkamo.rickandmorty.domain.model.Character

data class MainUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
