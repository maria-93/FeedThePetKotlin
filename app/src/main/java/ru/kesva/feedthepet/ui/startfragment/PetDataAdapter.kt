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
import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.getFormattedTime
import javax.inject.Inject


class PetDataAdapter @Inject constructor(
    private val adapterClickHandler: AdapterClickHandler
) : RecyclerView.Adapter<PetDataViewHolder>() {

    var petDataList: List<PetData> = listOf()
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

    override fun getItemCount(): Int = petDataList.size


    override fun onBindViewHolder(holder: PetDataViewHolder, position: Int) {
        petDataList[position].let { holder.bind(it, adapterClickHandler) }
    }

    override fun onViewRecycled(holder: PetDataViewHolder) {
        super.onViewRecycled(holder)
        holder.unbind()
    }

    interface AdapterClickHandler {
        fun petFedButtonClicked(
            petData: PetData,
            myCountDownTimer: MyCountDownTimer,
            textView: TextView
        )

        fun cancelAlarmButtonClicked(petData: PetData)
        fun editButtonClicked(petData: PetData)
    }

}

class PetDataViewHolder @Inject constructor(
    private val binding: LayoutForRvBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val tvForTimer: TextView = binding.tvremaintime
    private val timer: MyCountDownTimer = MyCountDownTimer()


    fun bind(
        petData: PetData,
        adapterClickHandler: PetDataAdapter.AdapterClickHandler
    ) {
        binding.petData = petData
        binding.adapterClickHandler = adapterClickHandler
        binding.timer = timer
        Log.d("Timer", "bind: ${petData.petName}")
        tvForTimer.text = getFormattedTime(petData.timeInterval)
        setListeners(petData)

        binding.executePendingBindings()
    }

    fun unbind() {
        timer.stop()
    }

    private fun setListeners(petData: PetData)  {
        timer.onTickListener = {
            Log.d("Timer", "onTickListener: $it животного ${petData.petName}")
            tvForTimer.text = getFormattedTime(it)
        }
    }
}



