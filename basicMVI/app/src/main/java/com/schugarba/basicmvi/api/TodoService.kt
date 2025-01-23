package com.schugarba.basicmvi.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TodoService {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private fun getRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: TodoApi = getRetrofit().create(TodoApi::class.java)
}