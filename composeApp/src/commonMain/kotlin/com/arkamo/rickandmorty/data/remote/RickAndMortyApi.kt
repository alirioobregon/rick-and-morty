package com.arkamo.rickandmorty.data.remote

import com.arkamo.rickandmorty.data.mapper.toDomain
import com.arkamo.rickandmorty.data.remote.dto.CharacterResponseDto
import com.arkamo.rickandmorty.domain.CharacterResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RickAndMortyApi(private val client: HttpClient) {

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api"
    }

    suspend fun getCharacters(page: Int): CharacterResult {
        val response = client.get("$BASE_URL/character") {
            parameter("page", page)
        }

        return if (response.status.value == 200) {
            val body = response.body<CharacterResponseDto>()
            CharacterResult.Success(body.results.map { it.toDomain() })
        } else {
            CharacterResult.Error("Error en el servicio ${response.status.value}")
        }
    }

}
