package com.schugarba.basicmvi.api

class AnimalRepo( private val api:AnimalApi) {
    suspend fun getAnimals() = api.getAnimals()
}




