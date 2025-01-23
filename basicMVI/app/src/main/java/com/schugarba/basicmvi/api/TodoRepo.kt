package com.schugarba.basicmvi.api

class TodoRepo(private val api:TodoApi) {
    suspend fun getTodos() = api.getAnimals()
}




