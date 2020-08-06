package ru.kesva.feedthepet.data.model

import ru.kesva.feedthepet.domain.model.Pet
import javax.inject.Inject

data class Buffer @Inject constructor(
    private val _pet: Pet,
    private val _petDataAction: PetDataAction
){
    val pet: Pet = _pet
    val petDataAction: PetDataAction = _petDataAction

}