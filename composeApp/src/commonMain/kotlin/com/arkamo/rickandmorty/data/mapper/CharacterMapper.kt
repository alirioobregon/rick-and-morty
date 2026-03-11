package com.arkamo.rickandmorty.data.mapper

import com.arkamo.rickandmorty.data.remote.dto.CharacterDto
import com.arkamo.rickandmorty.domain.model.Character

fun CharacterDto.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    image = image
)
