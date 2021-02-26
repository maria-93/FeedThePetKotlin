package ru.kesva.feedthepet.binding

import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.LocaleListCompat
import androidx.databinding.BindingAdapter
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
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
fun TextView.textForNextFeeding(pet: Pet) {
    val timeInFuture = pet.timeInFuture
    val currentLocale = LocaleListCompat.getDefault()[0]
    val format = DateFormat.getBestDateTimePattern(
        currentLocale, "HH:mm MMM dd"
    )

    if (timeInFuture > 0) {
        visibility = View.VISIBLE
        val formattedDate = SimpleDateFormat(format, currentLocale).format(timeInFuture)
        text = context.getString(R.string.feed_me_at, formattedDate)
    } else {
        text = context.getString(R.string.timer_stopped)
    }

}

@BindingAdapter("bindTimer", "bindTextView", "bindPet")
fun bindDataForTimerLaunch(view: View, timer: MyCountDownTimer, textView: TextView, pet: Pet) {
    val remainTime = pet.timeInFuture - System.currentTimeMillis()
    if (remainTime > 0) {
        timer.start(remainTime)
    }
}




