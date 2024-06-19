package com.example.hahh

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class JadwalActivity : AppCompatActivity() {

    lateinit var rincian : CardView
    lateinit var role : TextView
    lateinit var label : TextView
    lateinit var icon : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_jadwal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        role = findViewById(R.id.role)
        rincian = findViewById(R.id.CardView_torincian)
        label = findViewById(R.id.label_jadwal)
        icon = findViewById(R.id.tambah_jadwal)

        val id = intent.getIntExtra("id",0).toString()
        role.text= id

        label.text = intent.getStringExtra("title")

        val labelDetail = intent.getStringExtra("title_d")

        if (id == "1"){
            icon.visibility = View.GONE
            val role = 1
            rincian.setOnClickListener{
                val intent = Intent(
                    this,
                    RincianActivity::class.java
                ).apply {putExtra("title_d",labelDetail);putExtra("id",role)}
                startActivity(intent)
            }
        }
        else{
            icon.visibility = View.VISIBLE
            rincian.setOnClickListener{
                val intent = Intent(
                    this,
                    RincianActivity::class.java
                ).apply {putExtra("title_d",labelDetail)}
                startActivity(intent)
            }
        }
    }
}