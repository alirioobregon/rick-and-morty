package com.arkamo.rickandmorty.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arkamo.rickandmorty.domain.CharacterResult
import com.arkamo.rickandmorty.domain.CharacterUseCase
import com.arkamo.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: CharacterUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        getCharacters()
    }

    fun getCharacters(page: Int = 1) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val listCharacter = useCase.getListCharacter(page)
            listCharacter
                .onSuccess { result ->
                    when (result) {
                        is CharacterResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    characters = result.list,
                                    isLoading = false
                                )
                            }
                        }

                        is CharacterResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.error
                                )
                            }
                        }
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error al cargar personajes"
                        )
                    }
                }
        }
    }

    fun retry() = getCharacters()
}
