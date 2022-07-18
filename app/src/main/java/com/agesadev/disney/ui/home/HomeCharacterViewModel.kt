package com.agesadev.disney.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.agesadev.disney.domain.model.Character
import com.agesadev.disney.domain.use_case.GetAllCharactersUseCase
import com.agesadev.disney.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeCharacterViewModel @Inject constructor(private val getAllCharactersUseCase: GetAllCharactersUseCase) :
    ViewModel() {
    private var _characterList = MutableLiveData<PagingData<Character>>()
    val characterList: LiveData<PagingData<Character>> get() = _characterList

    private val _character = MutableStateFlow(CharacterState())
    val character: StateFlow<CharacterState> get() = _character

    init {
        getAllCharacters()
    }

    private fun getAllCharacters() {
        viewModelScope.launch {
            getAllCharactersUseCase().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _character.value = CharacterState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _character.value =
                            CharacterState(isLoading = false, data = result.data?.first())
                    }
                    is Resource.Error -> {
                        _character.value =
                            CharacterState(isLoading = false, error = result.message ?: "")
                    }
                }

            }.launchIn(viewModelScope)

        }
    }

//    fun getAllCharacters() {
//        viewModelScope.launch {
//            getAllCharactersUseCase().collectLatest { result ->
//                when (result) {
//                    is Resource.Success -> {
//                        _characterList.value = result.data?.first()
//                    }
//                }
//
//            }
//        }
//    }


}