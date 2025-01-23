package com.schugarba.basicmvi.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.schugarba.basicmvi.api.TodoApi
import com.schugarba.basicmvi.api.TodoRepo

class ViewModelFactory(private val api: TodoApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(TodoRepo(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}