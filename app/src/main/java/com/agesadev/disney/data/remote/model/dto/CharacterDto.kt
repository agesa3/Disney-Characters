package com.agesadev.disney.data.remote.model.dto

import com.agesadev.disney.domain.model.Character
import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("__v")
    val v: Int,
    @SerializedName("_id")
    val id: Int,
    @SerializedName("allies")
    val allies: List<String>,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("enemies")
    val enemies: List<String>,
    @SerializedName("films")
    val films: List<String>,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("parkAttractions")
    val parkAttractions: List<String>,
    @SerializedName("shortFilms")
    val shortFilms: List<String>,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("tvShows")
    val tvShows: List<String>,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("videoGames")
    val videoGames: List<String>
)

fun CharacterDto.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        imageUrl = imageUrl,
        sourceUrl = sourceUrl,
        url = url,
        allies = allies,
        enemies = enemies,
        films = films,
        shortFilms = shortFilms,
        tvShows = tvShows,
        videoGames = videoGames,
        parkAttractions = parkAttractions
    )
}