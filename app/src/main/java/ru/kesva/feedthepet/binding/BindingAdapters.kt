package ru.kesva.feedthepet.binding

import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.kesva.feedthepet.MyCountDownTimer
import ru.kesva.feedthepet.domain.model.Pet
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, petImageUri: String) {
    Glide
        .with(imageView)
        .load(petImageUri)
        .apply(RequestOptions.circleCropTransform())
        .into(imageView)
}

@BindingAdapter("textForNextFeeding")
fun textForNextFeeding(textView: TextView, calendar: Calendar) {
    val format = DateFormat.getBestDateTimePattern(
        Locale.getDefault(), "dd MMMM HH:mm"
    )
    val df = SimpleDateFormat(format, Locale.getDefault()).format(calendar.time)
    textView.text = df
}

@BindingAdapter("bindTimer", "bindTextView", "bindPet")
fun bindDataForTimerLaunch(view: View, timer: MyCountDownTimer, textView: TextView, pet: Pet) {
    val remainTime = pet.timeInFuture - System.currentTimeMillis()
    if (remainTime > 0) {
        timer.start(remainTime)

    }
}



