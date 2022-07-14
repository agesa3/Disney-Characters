package com.agesadev.disney.ui.home

import androidx.paging.PagingData
import com.agesadev.disney.domain.model.Character
import java.util.concurrent.Flow

data class CharacterState(
    val isLoading: Boolean = false,
    val data: PagingData<Character> = PagingData.empty(),
    val error: Boolean = false
)