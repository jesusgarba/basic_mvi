package com.schugarba.basicmvi.api

import com.schugarba.basicmvi.model.Todo
import retrofit2.http.GET

interface TodoApi {
    @GET("todos")
    suspend fun getAnimals(): List<Todo>
}