package com.arkamo.rickandmorty

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform