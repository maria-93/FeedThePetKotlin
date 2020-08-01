package ru.kesva.feedthepet.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "petData")
class PetData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var petName: String,
    //var timeForNextFeeding: Calendar,
    var timeInterval: Long,
    var petImageURI: String
) {


    fun getCopy() = PetData(
        this.id,
        this.petName,
        //this.timeForNextFeeding,
        this.timeInterval,
        this.petImageURI
    )
}


