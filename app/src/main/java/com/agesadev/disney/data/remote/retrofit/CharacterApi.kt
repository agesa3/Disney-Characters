package com.agesadev.disney.data.remote.retrofit

import com.agesadev.disney.data.remote.model.CharacterWrapper
import com.agesadev.disney.data.remote.model.dto.CharacterDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("characters")
    suspend fun getCharacters(
        @Query("page")
        page: Int,
    ): CharacterWrapper

    //get character by id
    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id")
        id: Int,
    ): CharacterDto

}