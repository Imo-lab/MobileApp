package com.example.hahh

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetAllPenyelidikan: AppCompatActivity(){
    private lateinit var namaText: TextView
    private lateinit var perkaraText: TextView
    private lateinit var jaksaText: TextView
    private lateinit var dateText: TextView
    private lateinit var getByIdText: Button
    override fun
}
private fun GetAllPenyelidikan(){
    PenyelidikanClient.instance.getAll().enqueue(object : Callback<List<Datapenyelidikan>>{
        override fun onResponse(
            call: Call<List<Datapenyelidikan>>,
            response: Response<List<Datapenyelidikan>>
        ) {
            if(response.isSuccessful){

            }
        }
    }