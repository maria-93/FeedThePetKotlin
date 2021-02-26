package ru.kesva.feedthepet.ui.startfragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
    var timerMap: MutableMap<Int, MyCountDownTimer> = mutableMapOf()

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
            holder.bind(it, adapterClickHandler, timerMap)
        }
    }

    override fun onViewRecycled(holder: PetDataViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind(timerMap)
        Log.d("Tick", "onViewRecycled: $holder")

    }

    override fun onViewDetachedFromWindow(holder: PetDataViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind(timerMap)
        Log.d("Tick", "onViewDetachedFromWindow: $holder")
    }

    override fun onViewAttachedToWindow(holder: PetDataViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind(petList[holder.adapterPosition], adapterClickHandler, timerMap)
        Log.d("Tick", "onViewAttachedToWindow: $holder")

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
        // у каждого животного может быть только 1 таймер.
        // При создании новых животных все таймеры добавляются в map
        // При вызове этого метода все таймеры будут остановлены.
        timerMap.forEach { entry ->
            val timer = entry.value
            timer.stop()
        }
    }

    fun stopTimerForPet(pet: Pet) {
        val timer = timerMap[pet.id]
        timer?.stop()
        timerMap.remove(pet.id)
        Log.d("Tick", "stopTimerForPet: name ${pet.petName} id pet ${pet.id}")
    }
}

class PetDataViewHolder @Inject constructor(
    private val binding: LayoutForRvBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val tvForTimer: TextView = binding.tvremaintime
    private var timer: MyCountDownTimer = MyCountDownTimer()


    fun bind(
        pet: Pet,
        adapterClickHandler: PetAdapter.AdapterClickHandler,
        timerMap: MutableMap<Int, MyCountDownTimer>
    ) {
        binding.pet = pet
        binding.adapterClickHandler = adapterClickHandler
        binding.timer = timer

        if (timerMap.containsKey(pet.id)) {
            //do nothing
        } else {
            Log.d("Tick", "bind: timer $timer")
            timerMap[pet.id] = timer
        }
        tvForTimer.text = getFormattedTime(pet.timeInterval)


        setListeners()

        binding.executePendingBindings()
    }

    fun unbind(timerMap: MutableMap<Int, MyCountDownTimer>) {
        Log.d("Tick", "unbind: $timer")
        timer.stop()
        timerMap.values.remove(timer)
        Log.d("Tick", "unbind: map size ${timerMap.size}")
    }


    private fun setListeners() {
        timer.onTickListener = {
            Log.d("Tick", "setListeners: $it")
            tvForTimer.text = getFormattedTime(it)
        }
    }
}



