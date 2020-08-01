package ru.kesva.feedthepet.ui.viewmodel

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.kesva.feedthepet.MyCountDownTimer
import ru.kesva.feedthepet.data.model.Buffer
import ru.kesva.feedthepet.data.model.Event
import ru.kesva.feedthepet.data.model.PetDataAction
import ru.kesva.feedthepet.domain.model.PetData
import ru.kesva.feedthepet.domain.repository.Repository
import ru.kesva.feedthepet.domain.usecases.*
import ru.kesva.feedthepet.ui.addnewpetfragment.PetCreationClickHandler
import ru.kesva.feedthepet.ui.startfragment.PetDataAdapter
import java.util.*
import javax.inject.Inject

class PetDataViewModel @Inject constructor(
    private val unregisterAlarmUseCase: UnregisterAlarmUseCase,
    private val startTimerUseCase: StartTimerUseCase,
    private val stopTimerUseCase: StopTimerUseCase,
    private val addNewPetUseCase: AddNewPetUseCase,
    private val updatePetUseCase: UpdatePetUseCase,
    private val deletePetUseCase: DeletePetUseCase,
    private val petFedUseCase: PetFedUseCase,
    private val repositoryImpl: Repository
) : ViewModel(), PetDataAdapter.AdapterClickHandler, PetCreationClickHandler {

    private lateinit var buffer: Buffer

    private val _createNewPet: MutableLiveData<Event<Any>> = MutableLiveData()
    val createNewPet: LiveData<Event<Any>> = _createNewPet

    private val _petFedButtonClicked: MutableLiveData<Event<Any>> = MutableLiveData()
    val petFedButtonClicked: LiveData<Event<Any>> = _petFedButtonClicked

    private val _editButtonClicked: MutableLiveData<Event<Any>> = MutableLiveData()
    val editButtonClicked: LiveData<Event<Any>> = _editButtonClicked

    val allPetLiveData: LiveData<List<PetData>>
        get() = repositoryImpl.getAllPetData()

    fun createPet() {
        val petData: PetData = getNewPet()
        buffer = Buffer(
            petData,
            PetDataAction.CREATE_PET
        )
        _createNewPet.postValue(Event(Any()))
    }

    override fun getBuffer(): Buffer {
        return buffer
    }

    override fun petFedButtonClicked(
        petData: PetData,
        myCountDownTimer: MyCountDownTimer,
        textView: TextView
    ) {
        Log.d("Timer", "petFedButtonClicked: ")
        var timeInFuture = System.currentTimeMillis() + petData.timeInterval
        var remainTime = timeInFuture - System.currentTimeMillis()
        Log.d("Timer", "petFedButtonClicked: животное ${petData.petName} с интервалом $remainTime")
        myCountDownTimer.start(remainTime)


       /* viewModelScope.launch {
            petFedUseCase.petFed(petData)

        }*/
        _petFedButtonClicked.value = Event(Any())

    }


    override fun cancelAlarmButtonClicked(petData: PetData) {
        unregisterAlarmUseCase.unregisterAlarm(petData.id)
    }

    override fun editButtonClicked(petData: PetData) {
        buffer = Buffer(
            petData,
            PetDataAction.UPDATE_PET
        )
        _editButtonClicked.value = Event(Any())
    }

    override fun onOkButtonClicked() {
        viewModelScope.launch {
            when (buffer.petDataAction) {
                PetDataAction.CREATE_PET -> addNewPetUseCase.addNewPet(buffer.petData)
                PetDataAction.UPDATE_PET -> updatePetUseCase.updatePet(buffer.petData)
            }
        }
    }

    private fun getNewPet(): PetData {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = 0
        return PetData(0, "", 0, "")
    }


}