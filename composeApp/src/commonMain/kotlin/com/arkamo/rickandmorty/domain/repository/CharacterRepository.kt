package com.arkamo.rickandmorty.domain.repository

import com.arkamo.rickandmorty.domain.CharacterResult
import com.arkamo.rickandmorty.domain.model.Character

interface CharacterRepository {
    suspend fun getCharacters(page: Int = 1): CharacterResult
}
