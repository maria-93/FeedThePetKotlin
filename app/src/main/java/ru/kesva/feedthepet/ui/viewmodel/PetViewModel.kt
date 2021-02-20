package ru.kesva.feedthepet.ui.viewmodel

import android.content.DialogInterface
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
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.AlarmRepository
import ru.kesva.feedthepet.domain.repository.Repository
import ru.kesva.feedthepet.domain.usecases.AddNewPetUseCase
import ru.kesva.feedthepet.domain.usecases.DeletePetUseCase
import ru.kesva.feedthepet.domain.usecases.MakePetFedUseCase
import ru.kesva.feedthepet.domain.usecases.UpdatePetUseCase
import ru.kesva.feedthepet.getFormattedTime
import ru.kesva.feedthepet.getRemainTime
import ru.kesva.feedthepet.ui.addnewpetfragment.PetCreationClickHandler
import ru.kesva.feedthepet.ui.startfragment.PetAdapter
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

    private val _cancelAlarmButtonClicked: MutableLiveData<Event<Unit>> = MutableLiveData()
    val cancelAlarmButtonClicked: LiveData<Event<Unit>> = _cancelAlarmButtonClicked

    private val _createNewPet: MutableLiveData<Event<Unit>> = MutableLiveData()
    val createNewPet: LiveData<Event<Unit>> = _createNewPet

    private val _deletePet: MutableLiveData<Event<Pet>> = MutableLiveData()
    val deletePet: LiveData<Event<Pet>> = _deletePet

    private val _petFedButtonClicked: MutableLiveData<Event<Unit>> = MutableLiveData()
    val petFedButtonClicked: LiveData<Event<Unit>> = _petFedButtonClicked

    private val _editButtonClicked: MutableLiveData<Event<Unit>> = MutableLiveData()
    val editButtonClicked: LiveData<Event<Unit>> = _editButtonClicked

    val allPetLiveData: LiveData<List<Pet>>
        get() = repository.getAllPetData()


    fun createPet() {
        val pet: Pet = getNewPet()
        buffer = Buffer(
            pet,
            PetDataAction.CREATE_PET
        )
        _createNewPet.postValue(Event(Unit))

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
            _petFedButtonClicked.value = Event(Unit)
            Log.d("Test!", "petFedButtonClicked: thread ${Thread.currentThread().name}")
        }
    }

    override fun cancelAlarmButtonClicked(pet: Pet) {
        alarmRepository.cancelAlarm(pet)
        _cancelAlarmButtonClicked.value = Event(Unit)
    }

    override fun editButtonClicked(pet: Pet) {
        buffer = Buffer(
            pet,
            PetDataAction.UPDATE_PET
        )
        _editButtonClicked.value = Event(Unit)
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

    override fun onOkButtonClicked(pet: Pet) {
        viewModelScope.launch {
            when (buffer.petDataAction) {
                PetDataAction.CREATE_PET -> addNewPetUseCase.addNewPet(buffer.pet)
                PetDataAction.UPDATE_PET -> {
                    Log.d("Test!", "onOkButtonClicked: update branch")
                    Log.d(
                        "Test!", "onOkButtonClicked: ${
                            getFormattedTime(pet.timeInterval)
                        }"
                    )
                    alarmRepository.cancelAlarm(pet)
                    Log.d("Test!", "onOkButtonClicked: cancelAlarm!")
                    updatePetUseCase.updatePet(buffer.pet)
                }
            }
        }
    }

    private fun getNewPet(): Pet {
        return Pet(0, "", 0, "", 0)
    }

}

