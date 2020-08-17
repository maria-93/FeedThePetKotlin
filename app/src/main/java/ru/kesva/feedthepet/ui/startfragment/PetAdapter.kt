package ru.kesva.feedthepet.ui.startfragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kesva.feedthepet.MyCountDownTimer
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.databinding.LayoutForRvBinding
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.getFormattedTime
import javax.inject.Inject


class PetAdapter @Inject constructor(
    private val adapterClickHandler: AdapterClickHandler
) : RecyclerView.Adapter<PetDataViewHolder>() {
    var timerSet: MutableSet<MyCountDownTimer> = mutableSetOf()

    var petList: MutableList<Pet> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetDataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutForRvBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_for_rv,
            parent,
            false
        )
        return PetDataViewHolder(binding)
    }

    override fun getItemCount(): Int = petList.size


    override fun onBindViewHolder(holder: PetDataViewHolder, position: Int) {
        petList[position].let {
            holder.bind(it, adapterClickHandler, timerSet)
        }

    }

    override fun onViewRecycled(holder: PetDataViewHolder) {
        super.onViewRecycled(holder)
        Log.d("Timer", "onViewRecycled: $holder")
        holder.unbind(timerSet)
    }

    override fun onViewDetachedFromWindow(holder: PetDataViewHolder) {
        super.onViewDetachedFromWindow(holder)
        Log.d("Timer", "onViewDetachedFromWindow: ")
        holder.unbind(timerSet)
    }

    interface AdapterClickHandler {
        fun petFedButtonClicked(
            pet: Pet,
            myCountDownTimer: MyCountDownTimer,
            remainTimeTextView: TextView
        )

        fun cancelAlarmButtonClicked(pet: Pet)
        fun editButtonClicked(pet: Pet)
        fun deleteButtonClicked(pet: Pet)
    }

    fun stopTimers() {
        timerSet.forEach { it.stop() }
    }


}

class PetDataViewHolder @Inject constructor(
    private val binding: LayoutForRvBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val tvForTimer: TextView = binding.tvremaintime
    private val timer: MyCountDownTimer = MyCountDownTimer()


    fun bind(
        pet: Pet,
        adapterClickHandler: PetAdapter.AdapterClickHandler,
        timerSet: MutableSet<MyCountDownTimer>
    ) {
        binding.pet = pet
        binding.adapterClickHandler = adapterClickHandler
        binding.timer = timer

        timerSet.add(timer)
        Log.d("Timer", "bind: ${pet.petName} $this")
        tvForTimer.text = getFormattedTime(pet.timeInterval)
        setListeners(pet)

        binding.executePendingBindings()
    }

    fun unbind(timerSet: MutableSet<MyCountDownTimer>) {
        timer.stop()
        timerSet.remove(timer)

    }

    private fun setListeners(pet: Pet) {
        timer.onTickListener = {
            Log.d("Timer", "onTickListener: $it животного ${pet.petName}")
            tvForTimer.text = getFormattedTime(it)
        }
    }


}



