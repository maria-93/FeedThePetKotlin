package ru.kesva.feedthepet.ui.startfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.databinding.LayoutForRvBinding
import ru.kesva.feedthepet.domain.model.PetData
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
    }

    interface AdapterClickHandler {
        fun petFedButtonClicked(petData: PetData)
        fun cancelAlarmButtonClicked(petData: PetData)
        fun editButtonClicked(petData: PetData)
        fun startTimer(petData: PetData)

    }

}

class PetDataViewHolder @Inject constructor(
    private val binding: LayoutForRvBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        petData: PetData,
        adapterClickHandler: PetDataAdapter.AdapterClickHandler
    ) {
        binding.petData = petData
        binding.adapterClickHandler = adapterClickHandler
        binding.executePendingBindings()
        binding.root.setTag(R.id.petdata_key, petData)
    }
}



