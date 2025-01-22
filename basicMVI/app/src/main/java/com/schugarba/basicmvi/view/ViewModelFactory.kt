package com.schugarba.basicmvi.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.schugarba.basicmvi.api.AnimalApi
import com.schugarba.basicmvi.api.AnimalRepo

class ViewModelFactory(private val api: AnimalApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(AnimalRepo(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}