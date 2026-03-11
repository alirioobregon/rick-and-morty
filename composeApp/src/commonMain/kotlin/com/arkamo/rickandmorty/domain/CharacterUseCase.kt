package com.arkamo.rickandmorty.domain

import com.arkamo.rickandmorty.domain.model.Character
import com.arkamo.rickandmorty.domain.repository.CharacterRepository

class CharacterUseCase(private val repository: CharacterRepository) {
    suspend fun getListCharacter(page: Int = 1): Result<CharacterResult> = runCatching { repository.getCharacters(page) }
}


sealed class CharacterResult {
    data class Success(val list: List<Character>) : CharacterResult()
    data class Error(val error: String): CharacterResult()
}