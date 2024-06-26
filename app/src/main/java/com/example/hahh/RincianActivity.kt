package com.example.hahh

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RincianActivity : AppCompatActivity() {

    lateinit var label_d : TextView
    lateinit var role : TextView
    lateinit var icon_del : ImageView
    lateinit var icon_edt : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rincian)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        label_d = findViewById(R.id.label_detail)
        label_d.text = intent.getStringExtra("title_d")

        role = findViewById(R.id.role)
        val id = intent.getIntExtra("id",0).toString()
        role.text= id

        icon_del = findViewById(R.id.imageView_delete_jadwal)
        icon_edt = findViewById(R.id.imageView_edit_jadwal)

        if (id == "1"){
            icon_del.visibility = View.GONE
            icon_edt.visibility = View.GONE
        }
        else{
            icon_del.visibility = View.VISIBLE
            icon_edt.visibility = View.VISIBLE
        }

    }
}