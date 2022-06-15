package com.agesadev.disney.data.remote.model

import com.agesadev.disney.data.remote.model.dto.CharacterDto
import com.google.gson.annotations.SerializedName

data class CharacterWrapper(
    @SerializedName("data")
    val characters: List<CharacterDto>,
    @SerializedName("count")
    val count: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("nextPage")
    val nextPage: String
)