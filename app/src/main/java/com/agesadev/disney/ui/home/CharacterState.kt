package com.agesadev.disney.ui.home

import androidx.paging.PagingData
import com.agesadev.disney.domain.model.Character


data class CharacterState(
    val isLoading: Boolean = false,
    val data: PagingData<Character>? = null,
    val error: String = ""
)