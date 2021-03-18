package ru.kesva.feedthepet

import androidx.recyclerview.widget.DiffUtil
import ru.kesva.feedthepet.domain.model.Pet

class PetDiffUtilCallback() : DiffUtil.ItemCallback<Pet>() {

    override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem.petName == newItem.petName && oldItem.timeInterval == newItem.timeInterval
                && oldItem.timeInFuture == newItem.timeInFuture
                && oldItem.petImageURI == newItem.petImageURI
    }
}