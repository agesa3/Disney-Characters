package com.agesadev.disney.ui.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.domain.use_case.GetSingleCharacterUseCase
import com.agesadev.disney.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val singleCharacterUseCase: GetSingleCharacterUseCase
) : ViewModel() {

    private val mCharacter = MutableLiveData<Character>()


    fun character(): LiveData<Character> = mCharacter

    @SuppressLint("NullSafeMutableLiveData")
    fun getSingleCharacter(id: Int) {
        singleCharacterUseCase(id).onEach { characterResource ->
            when (characterResource) {
                is Resource.Success -> {
                    val characters = characterResource.data
                    mCharacter.postValue(characters)
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }

            }
        }.launchIn(viewModelScope)

    }

}