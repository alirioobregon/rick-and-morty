package com.arkamo.rickandmorty.data.repository

import com.arkamo.rickandmorty.data.mapper.toDomain
import com.arkamo.rickandmorty.data.remote.RickAndMortyApi
import com.arkamo.rickandmorty.domain.CharacterResult
import com.arkamo.rickandmorty.domain.model.Character
import com.arkamo.rickandmorty.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): CharacterResult =
        api.getCharacters(page)
}
