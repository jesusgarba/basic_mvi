package com.schugarba.basicmvi.view

import com.schugarba.basicmvi.model.Todo

sealed class MainState {

    object Idle: MainState()
    object Loading: MainState()
    data class Todos(val todos: List<Todo>): MainState()
    data class Error(val error: String): MainState()
}