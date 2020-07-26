package ru.kesva.feedthepet

import android.app.Application
import ru.kesva.feedthepet.di.AppComponent
import ru.kesva.feedthepet.di.DaggerAppComponent

open class FeedThePetApplication : Application() {
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent() =
        DaggerAppComponent.factory().create(applicationContext)

    override fun onCreate() {
        super.onCreate()
        appComponent.provideDependenciesFor(this)
    }
}