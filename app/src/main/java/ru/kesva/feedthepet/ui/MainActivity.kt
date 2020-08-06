package ru.kesva.feedthepet.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.kesva.feedthepet.FeedThePetApplication
import ru.kesva.feedthepet.R
import ru.kesva.feedthepet.di.ViewModelFactory
import ru.kesva.feedthepet.di.subcomponents.MainActivityComponent
import ru.kesva.feedthepet.extensions.getViewModel
import ru.kesva.feedthepet.ui.viewmodel.PetViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var component: MainActivityComponent

    @Inject
    lateinit var factory: ViewModelFactory

    @Inject
    lateinit var viewModel: PetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    private fun injectDependencies() {
        component =
            (applicationContext as FeedThePetApplication).appComponent.mainActivityComponent()
                .create()
        component.provideDependenciesFor(this)
        viewModel = getViewModel(factory)
    }
}
