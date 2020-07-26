
package ru.kesva.feedthepet.binding

import android.text.format.DateFormat
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter( "loadImage" )
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
        Locale.getDefault(), "dd MMMM HH:mm")
    val df = SimpleDateFormat(format, Locale.getDefault()).format(calendar.time)
    textView.text = df
}


