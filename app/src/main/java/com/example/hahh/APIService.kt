package com.example.hahh

import retrofit2.Call
import retrofit2.http.*

data class User(val namaDepan: String, val namaBelakang: String, val email: String, val password: String)
data class Login(val email: String, val passsword: String)
data class ForgetPass(val newPass: String, val confirmPass: String)
//interface APIService {
//    @GET("users")
//    fun getUsers(): Call<List<User>>
//
//    @POST("users")
//    fun createUser(@Body user: User): Call<User>
//
//    @PUT("users/{id}")
//    fun updateUser(@Path("id") id: Int, @Body user: User): Call<User>
//
//    @DELETE("users/{id}")
//    fun deleteUser(@Path("id") id: Int): Call<Void>
//}

interface APIService {
    @POST("exec?action=register")
    fun register(@Body user: User): Call<User>

    @POST("exec?action=login")
    fun login(@Body user: Login): Call<Login>

    @POST("exec?action=updatePassword")
    fun forgetPassword(@Query("id") id: String, @Body user: ForgetPass): Call<ForgetPass>
}
