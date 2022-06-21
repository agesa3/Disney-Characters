package com.agesadev.disney.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.domain.use_case.GetAllCharactersUseCase
import com.agesadev.disney.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeCharacterViewModel @Inject constructor(private val getAllCharactersUseCase: GetAllCharactersUseCase) : ViewModel() {
    private val characterList = MutableLiveData<PagingData<Character>>()

    init {
        getCharacters()
    }

    fun getCharacters(): Flow<Resource<Flow<PagingData<Character>>>> {
        return getAllCharactersUseCase()
    }

    fun characters(): LiveData<PagingData<Character>> = characterList
}