package ru.kesva.feedthepet.domain.model

import androidx.databinding.ObservableField
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ru.kesva.feedthepet.getFormattedTime
import java.util.*

@Entity(tableName = "petData")
class PetData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var petName: String,
    var calendar: Calendar,
    var timeInterval: Long,
    var petImageURI: String
) {
    @Ignore
    private val _remainTime: ObservableField<String> = ObservableField()
    @Ignore
    val remainTime: ObservableField<String> = _remainTime

    init {
        _remainTime.set(getFormattedTime(0))
    }

    fun getCopy() = PetData(
        this.id,
        this.petName,
        this.calendar,
        this.timeInterval,
        this.petImageURI
    )
}

/*private fun getFormattedTime(timeInterval: Long): String {
    val minutes: Int = timeInterval.toInt() / (1000 * 60) % 60
    val hours: Int = timeInterval.toInt() / (1000 * 60 * 60) % 24
    val days: Int = timeInterval.toInt() / 1000 * 60 * 60 * 24
    return String.format(
        Locale.getDefault(),
        "%02dд.%02dч.%02dмин.", days, hours, minutes
    )
}*/

