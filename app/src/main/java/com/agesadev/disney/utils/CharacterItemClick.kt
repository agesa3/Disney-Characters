package com.agesadev.disney.utils

import com.agesadev.disney.domain.model.Character

interface CharacterItemClick {
    fun clickedCharacter(character: Character)
}