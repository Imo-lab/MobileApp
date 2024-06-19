package com.example.hahh

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {

    lateinit var penyidikan : CardView
    lateinit var penyelidikan : CardView
    lateinit var role : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        penyidikan = findViewById(R.id.CardView_PENYIDIKAN)
        penyelidikan = findViewById(R.id.CardView_PENYELIDIKAN)
        role = findViewById(R.id.role)

        val id = intent.getIntExtra("id",0).toString()
        role.text= id

        if (id == "1"){
            val role = 1
            penyidikan.setOnClickListener{
                val label_jadwal = "Jadwal Penyidikan"
                val label_detail = "Detail Penyidikan"
                val intent = Intent(
                    this,
                    JadwalActivity::class.java
                ).apply {putExtra("title",label_jadwal);putExtra("title_d",label_detail);putExtra("id",role)}
                startActivity(intent)
            }
            penyelidikan.setOnClickListener{
                val label_jadwal = "Jadwal Penyelidikan"
                val label_detail = "Detail Penyidikan"
                val intent = Intent(
                    this,
                    JadwalActivity::class.java
                ).apply {putExtra("title",label_jadwal);putExtra("title_d",label_detail);putExtra("id",role)}
                startActivity(intent)
            }
        }

        else{
            penyidikan.setOnClickListener{
                val label_jadwal = "Jadwal Penyidikan"
                val label_detail = "Detail Penyidikan"
                val intent = Intent(
                    this,
                    JadwalActivity::class.java
                ).apply {putExtra("title",label_jadwal);putExtra("title_d",label_detail)}
                startActivity(intent)
            }

            penyelidikan.setOnClickListener{
                val label_jadwal = "Jadwal Penyelidikan"
                val label_detail = "Detail Penyidikan"
                val intent = Intent(
                    this,
                    JadwalActivity::class.java
                ).apply {putExtra("title",label_jadwal);putExtra("title_d",label_detail)}
                startActivity(intent)
            }
        }
    }
}