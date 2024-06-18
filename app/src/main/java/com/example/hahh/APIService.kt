package com.example.hahh

import retrofit2.Call
import retrofit2.http.*

data class User(val namaDepan: String, val namaBelakang: String, val email: String, val password: String, val role: String)
data class Login(val email: String, val password: String)
data class ForgetPass(val newPass: String, val confirmPass: String)

interface APIService {
    @POST("?action=register")
    fun register(@Body user: User): Call<User>

    @POST("?action=login")
    fun login(@Body user: Login): Call<Login>

    @POST("?action=updatePassword")
    fun forgetPassword(@Query("id") id: String, @Body user: ForgetPass): Call<ForgetPass>
}
