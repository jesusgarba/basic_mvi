package com.schugarba.basicmvi.view

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.schugarba.basicmvi.api.TodoRepo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repo: TodoRepo) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    var state = mutableStateOf<MainState>(MainState.Idle)
        private set

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect { collector ->
                when (collector) {
                    is MainIntent.FetchTodos -> fetchTodos()
                }
            }
        }
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            state.value = MainState.Loading
            state.value = try {
                MainState.Todos(repo.getTodos())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }
}