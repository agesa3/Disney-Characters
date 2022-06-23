package com.agesadev.disney.domain.use_case

import androidx.paging.PagingData
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.domain.repository.CharacterRepository
import com.agesadev.disney.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    operator fun invoke() = flow {
        try {
            emit(Resource.Loading())
            val characters = characterRepository.getAllCharacters().flow
            emit(Resource.Success(characters))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Please check your internet connection."))
        }
    }

}