package ru.kesva.feedthepet.binding

import android.text.format.DateFormat
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.kesva.feedthepet.*
import ru.kesva.feedthepet.domain.model.Pet
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, petImageUri: String) {
    Glide
        .with(imageView)
        .load(petImageUri)
        .placeholder(R.drawable.image_placeholder)
        .apply(RequestOptions.circleCropTransform())
        .into(imageView)
}

@BindingAdapter("textForNextFeeding")
fun TextView.textForNextFeeding(pet: Pet) {
    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
        this, 10, 17, 1, TypedValue.COMPLEX_UNIT_DIP
    )
    val timeInFuture = pet.timeInFuture
    val locale = Locale.getDefault()
    val dateFormat = if (locale.language == "en" && locale.country == "US") {
        DateFormat.getBestDateTimePattern(locale, "MMMM d h:mm a")
    } else {
        DateFormat.getBestDateTimePattern(
            locale, "HH:mm MMM dd"
        )
    }

    if (timeInFuture > 0) {
        visibility = View.VISIBLE
        val formattedDate = SimpleDateFormat(dateFormat, locale).format(timeInFuture)
        text = context.getString(R.string.feed_me_at, formattedDate)

    } else {
        text = context.getString(R.string.timer_stopped)
    }

}

@BindingAdapter("bindPetNameTextView")
fun textForPetName(view: View, petName: TextView) {
    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
        petName, 15, 24, 1, TypedValue.COMPLEX_UNIT_DIP
    )
}
@BindingAdapter("bindTimer", "bindTextView", "bindPet")
fun bindDataForTimerLaunch(view: View, timer: MyCountDownTimer, textView: TextView, pet: Pet) {
    val remainTime = pet.timeInFuture - System.currentTimeMillis()
    if (remainTime > 0) {
        timer.start(remainTime)
    }
}

@BindingAdapter("timeBeforeFeeding")
fun TextView.timeBeforeFeeding(pet: Pet) {
    val time = (pet.timeInFuture - System.currentTimeMillis()).toInt()
    if (time <= 0) {
        text = context.getString(R.string.interval)
    }
}

@BindingAdapter("bindDaysPicker", "bindHoursPicker", "bindMinutesPicker", "bindPet")
fun timeForNumberPickers(view: View, daysPicker: NumberPicker, hoursPicker: NumberPicker, minutesPicker: NumberPicker, pet: Pet) {
    daysPicker.value = msToDays(pet.timeInterval)
    hoursPicker.value = msToHours(pet.timeInterval)
    minutesPicker.value = msToMinutes(pet.timeInterval)
}




