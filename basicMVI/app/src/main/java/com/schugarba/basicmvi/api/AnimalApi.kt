package com.schugarba.basicmvi.api

import com.schugarba.basicmvi.model.Animal
import retrofit2.http.GET

interface AnimalApi {
    @GET("animals.json")
    suspend fun getAnimals(): List<Animal>
}