package ru.kesva.feedthepet.ui.viewmodel

import android.content.DialogInterface
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
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.AlarmRepository
import ru.kesva.feedthepet.domain.repository.Repository
import ru.kesva.feedthepet.domain.usecases.AddNewPetUseCase
import ru.kesva.feedthepet.domain.usecases.DeletePetUseCase
import ru.kesva.feedthepet.domain.usecases.MakePetFedUseCase
import ru.kesva.feedthepet.domain.usecases.UpdatePetUseCase
import ru.kesva.feedthepet.getRemainTime
import ru.kesva.feedthepet.ui.addnewpetfragment.PetCreationClickHandler
import ru.kesva.feedthepet.ui.startfragment.PetAdapter
import java.util.*
import javax.inject.Inject

class PetViewModel @Inject constructor(
    private val addNewPetUseCase: AddNewPetUseCase,
    private val updatePetUseCase: UpdatePetUseCase,
    private val deletePetUseCase: DeletePetUseCase,
    private val makePetFedUseCase: MakePetFedUseCase,
    private val repository: Repository,
    private val alarmRepository: AlarmRepository
) : ViewModel(), PetAdapter.AdapterClickHandler, PetCreationClickHandler {

    private lateinit var buffer: Buffer

    private val _alertDialogInitiated: MutableLiveData<Event<Pet>> = MutableLiveData()
    val alertDialogInitiated: LiveData<Event<Pet>> = _alertDialogInitiated

    private val _createNewPet: MutableLiveData<Event<Any>> = MutableLiveData()
    val createNewPet: LiveData<Event<Any>> = _createNewPet

    private val _deletePet: MutableLiveData<Event<Pet>> = MutableLiveData()
    val deletePet: LiveData<Event<Pet>> = _deletePet

    private val _petFedButtonClicked: MutableLiveData<Event<Any>> = MutableLiveData()
    val petFedButtonClicked: LiveData<Event<Any>> = _petFedButtonClicked

    private val _editButtonClicked: MutableLiveData<Event<Any>> = MutableLiveData()
    val editButtonClicked: LiveData<Event<Any>> = _editButtonClicked

    val allPetLive: LiveData<List<Pet>>
        get() = repository.getAllPetData()


    fun createPet() {
        val pet: Pet = getNewPet()
        buffer = Buffer(
            pet,
            PetDataAction.CREATE_PET
        )
        _createNewPet.postValue(Event(Any()))

    }

    override fun getBuffer(): Buffer {
        return buffer
    }

    override fun petFedButtonClicked(
        pet: Pet,
        myCountDownTimer: MyCountDownTimer,
        remainTimeTextView: TextView
    ) {
        viewModelScope.launch {
            makePetFedUseCase(pet)
            myCountDownTimer.start(getRemainTime(pet.timeInFuture))
        }
    }

    override fun cancelAlarmButtonClicked(pet: Pet) {
        alarmRepository.cancelAlarm(pet)
    }

    override fun editButtonClicked(pet: Pet) {
        buffer = Buffer(
            pet,
            PetDataAction.UPDATE_PET
        )
        _editButtonClicked.value = Event(Any())
    }

    override fun deleteButtonClicked(pet: Pet) {
     _alertDialogInitiated.value = Event(pet)
    }

    fun positiveButtonClick(pet: Pet) = { _: DialogInterface, _: Int ->
        viewModelScope.launch {
            deletePetUseCase.deletePet(pet)
        }
        _deletePet.value =
            Event(
                Pet(
                    pet.id, pet.petName, pet.timeInterval, pet.petImageURI, pet.timeInFuture
                )
            )
    }

    override fun onOkButtonClicked() {
        viewModelScope.launch {
            when (buffer.petDataAction) {
                PetDataAction.CREATE_PET -> addNewPetUseCase.addNewPet(buffer.pet)
                PetDataAction.UPDATE_PET -> updatePetUseCase.updatePet(buffer.pet)
            }
        }
    }

    private fun getNewPet(): Pet {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = 0
        return Pet(0, "", 0, "", 0)
    }

}

