package ru.kesva.feedthepet.binding

import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
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
    val timeInFuture = pet.timeInFuture
    val locale = Locale.getDefault()
    var dateFormat = ""
    dateFormat = if (locale.language == "en" && locale.country == "US") {
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

@BindingAdapter("bindTimer", "bindTextView", "bindPet")
fun bindDataForTimerLaunch(view: View, timer: MyCountDownTimer, textView: TextView, pet: Pet) {
    view.post {
        textView.text = getFormattedTime(pet.timeInterval)
    }
    val remainTime = pet.timeInFuture - System.currentTimeMillis()
    if (remainTime > 0) {
        timer.start(remainTime)
    }
}

@BindingAdapter("bindDaysPicker", "bindHoursPicker", "bindMinutesPicker", "bindPet")
fun timeForNumberPickers(view: View, daysPicker: NumberPicker, hoursPicker: NumberPicker, minutesPicker: NumberPicker, pet: Pet) {
    daysPicker.value = msToDays(pet.timeInterval)
    hoursPicker.value = msToHours(pet.timeInterval)
    minutesPicker.value = msToMinutes(pet.timeInterval)
}




