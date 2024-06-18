package com.example.hahh

import retrofit2.Call
import retrofit2.http.*

data class User(val namaDepan: String, val namaBelakang: String, val email: String, val sandi: String, val role: String)
data class Login(val email: String, val sandi: String)
data class ForgetPass(val newPass: String, val confirmPass: String)

interface APIService {
    @POST("exec?action=register")
    fun register(@Body user: User): Call<User>

    @POST("exec?action=login")
    fun login(@Body user: Login): Call<Login>

    @POST("exec?action=updatePassword")
    fun forgetPassword(@Query("id") id: String, @Body user: ForgetPass): Call<ForgetPass>
}
