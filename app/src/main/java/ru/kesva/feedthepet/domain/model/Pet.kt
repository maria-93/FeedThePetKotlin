package ru.kesva.feedthepet.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "petData")
class Pet(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var petName: String,
    var timeInterval: Long,
    var petImageURI: String,
    var timeInFuture: Long
): Serializable {


    fun getCopy() = Pet(
        this.id,
        this.petName,
        this.timeInterval,
        this.petImageURI,
        this.timeInFuture
    )
}


