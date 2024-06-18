package com.example.hahh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.input_text_email)
        passwordEditText = findViewById(R.id.input_text_password)
        loginButton = findViewById(R.id.button)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val sandi = passwordEditText.text.toString().trim()

            if (email.isEmpty() || sandi.isEmpty()) {
                Toast.makeText(this, "Email dan Sandi harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                val loginUser = Login(email = email, sandi = sandi)
                loginUser(loginUser)
            }
        }
    }

    private fun loginUser(user: Login) {
        RetrofitClient.instance.login(user).enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if (response.isSuccessful) {
                    val loggedInUser = response.body()
                    Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(this@LoginActivity, "Login gagal: ${response.code()} - $errorBody}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@LoginActivity, "Login gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
