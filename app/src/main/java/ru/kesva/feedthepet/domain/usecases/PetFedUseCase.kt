package ru.kesva.feedthepet.domain.usecases

import android.content.Context
import ru.kesva.feedthepet.di.ApplicationContext
import ru.kesva.feedthepet.domain.repository.Repository
import javax.inject.Inject

class PetFedUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository,
    private val registerAlarmUseCase: RegisterAlarmUseCase
) {

}