package com.fkocak.witranslate.deleteAfter

import androidx.lifecycle.ViewModel
import com.fkocak.witranslate.deleteAfter.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    val characters = repository.getCharacters()
}