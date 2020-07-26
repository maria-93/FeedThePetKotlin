package ru.kesva.feedthepet.data.model

import ru.kesva.feedthepet.domain.model.PetData
import javax.inject.Inject

data class Buffer @Inject constructor(
    private val _petData: PetData,
    private val _petDataAction: PetDataAction
){
    val petData: PetData = _petData
    val petDataAction: PetDataAction = _petDataAction

}