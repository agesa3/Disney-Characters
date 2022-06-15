package com.agesadev.disney.domain.use_case

import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.domain.repository.CharacterRepository
import com.agesadev.disney.utils.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSingleCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(characterId: Int) = flow<Resource<Character>> {
        try {
            emit(Resource.Loading())
            val character = characterRepository.getSingleCharacter(characterId)
            emit(Resource.Success(character))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Please check your internet connection."))
        }
    }
}