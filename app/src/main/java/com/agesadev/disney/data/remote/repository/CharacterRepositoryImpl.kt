package com.agesadev.disney.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.agesadev.disney.data.remote.model.dto.toCharacter
import com.agesadev.disney.data.remote.paging.CharacterPagingSource
import com.agesadev.disney.data.remote.retrofit.CharacterApi
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterApi: CharacterApi
) : CharacterRepository {
    override suspend fun getAllCharacters(): Pager<Int, Character> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharacterPagingSource(characterApi)
            }
        )
    }

    override suspend fun getSingleCharacter(characterId: Int): Character {
        return characterApi.getCharacterById(characterId).toCharacter()
    }

}