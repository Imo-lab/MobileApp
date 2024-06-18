package com.example.hahh

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://script.google.com/macros/s/AKfycbxy3mX1V7gQprYF5HCWhif4MR-fnuwzmxVTzG61dY-IRA-tWR2Tps-JhvmCjCXnbSb5gg/exec/"

    val instance: APIService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(APIService::class.java)
    }
}
