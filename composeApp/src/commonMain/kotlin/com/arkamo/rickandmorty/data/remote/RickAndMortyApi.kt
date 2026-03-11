package com.arkamo.rickandmorty.data.remote

import com.arkamo.rickandmorty.data.remote.dto.CharacterResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RickAndMortyApi(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api"
    }

    suspend fun getCharacters(page: Int): CharacterResponseDto =
        client.get("$BASE_URL/character") {
            parameter("page", page)
        }.body()
}
