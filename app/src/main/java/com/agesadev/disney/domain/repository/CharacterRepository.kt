package com.agesadev.disney.domain.repository

import androidx.paging.Pager
import com.agesadev.disney.domain.model.Character

interface CharacterRepository {
    suspend fun getAllCharacters(): Pager<Int, Character>
    suspend fun getSingleCharacter(characterId: Int): Character
}