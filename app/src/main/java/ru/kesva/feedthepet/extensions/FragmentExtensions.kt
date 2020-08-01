package ru.kesva.feedthepet.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T : ViewModel> Fragment.getViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.getViewModel(
    factory: ViewModelProvider.Factory, viewModelStoreOwner: ViewModelStoreOwner
): T {
    return ViewModelProvider(viewModelStoreOwner, factory)[T::class.java]
}

inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.getViewModel(): T {
    return ViewModelProvider(this)[T::class.java]
}

