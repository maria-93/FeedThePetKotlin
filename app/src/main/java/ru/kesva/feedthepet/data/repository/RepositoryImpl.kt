package ru.kesva.feedthepet.data.repository

import android.app.AlarmManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kesva.feedthepet.data.source.local.PetDatabase
import ru.kesva.feedthepet.di.ApplicationContext
import ru.kesva.feedthepet.domain.model.Pet
import ru.kesva.feedthepet.domain.repository.Repository
import ru.kesva.feedthepet.getPendingIntentForCancel
import ru.kesva.feedthepet.getPendingIntentForNotification
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    petDatabase: PetDatabase
) : Repository {

    private val petDataDao = petDatabase.petDataDao()

   /* @Inject
    lateinit var alarmManager: AlarmManager*/

    override suspend fun update(pet: Pet) {
        withContext(Dispatchers.IO) {
            petDataDao.update(pet)
        }
    }

    override suspend fun insert(pet: Pet) {
        withContext(Dispatchers.IO) {
            petDataDao.insert(pet)
        }
    }

    override suspend fun delete(pet: Pet) {
        withContext(Dispatchers.IO) {
            petDataDao.delete(pet)
        }
    }

    override suspend fun deleteAllPetData() {
        withContext(Dispatchers.IO) {
            petDataDao.deleteAllPetData()
        }
    }

    override suspend fun getById(id: Int): Pet {
        TODO("Not yet implemented")
    }

    override fun getAllPetData(): LiveData<List<Pet>> {
        return petDataDao.getAllPetData()
    }

    override fun registerAndStartAlarm(
        petId: Int,
        triggerTime: Long
    ) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent =
            getPendingIntentForNotification(
                context,
                petId
            )
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            startAlarmShowToast()
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent
            )
            startAlarmShowToast()

        }
    }

    private fun startAlarmShowToast() {
        Toast.makeText(context, "Будильник запущен", Toast.LENGTH_SHORT).show()
    }

    override fun cancelAlarm(petId: Int) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = getPendingIntentForCancel(context, petId)
        alarmManager.cancel(pendingIntent)
        cancelAlarmShowToast()
    }

    private fun cancelAlarmShowToast() {
        Toast.makeText(context, "Будильник остановлен", Toast.LENGTH_SHORT).show()
    }

}