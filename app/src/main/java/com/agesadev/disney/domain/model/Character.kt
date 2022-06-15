package com.agesadev.disney.domain.model

data class Character(
    val id: Int,
    val allies: List<String?>? = null,
    val createdAt: String? = null,
    val enemies: List<String?>? = null,
    val films: List<String>? = null,
    val imageUrl: String? = null,
    val name: String? = null,
    val parkAttractions: List<String>? = null,
    val shortFilms: List<String?>? = null,
    val sourceUrl: String? = null,
    val tvShows: List<String?>? = null,
    val updatedAt: String? = null,
    val url: String? = null,
    val videoGames: List<String?>? = null
)