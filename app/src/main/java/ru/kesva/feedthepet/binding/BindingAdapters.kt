package ru.kesva.feedthepet.binding

import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.LocaleListCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ru.kesva.feedthepet.MyCountDownTimer
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.domain.model.Pet
import java.text.SimpleDateFormat

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
fun textForNextFeeding(textView: TextView, pet: Pet) {
    val timeInFuture = pet.timeInFuture
    val currentLocale = LocaleListCompat.getDefault()[0]
    val format = DateFormat.getBestDateTimePattern(
        currentLocale, "HH:mm MMM dd"
    )
    val df = SimpleDateFormat(format, currentLocale).format(timeInFuture)
    textView.text = df
}

@BindingAdapter("bindTimer", "bindTextView", "bindPet")
fun bindDataForTimerLaunch(view: View, timer: MyCountDownTimer, textView: TextView, pet: Pet) {
    val remainTime = pet.timeInFuture - System.currentTimeMillis()
    if (remainTime > 0) {
        timer.start(remainTime)
    }
}




