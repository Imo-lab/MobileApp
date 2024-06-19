package com.example.hahh

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var namaDepanEditText: EditText
    private lateinit var namaBelakangEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        namaDepanEditText = findViewById(R.id.input_first_name)
        namaBelakangEditText = findViewById(R.id.input_last_name)
        emailEditText = findViewById(R.id.input_email)
        passwordEditText = findViewById(R.id.input_sandi)
        signUpButton = findViewById(R.id.button)

        signUpButton.setOnClickListener {
            val namaDepan = namaDepanEditText.text.toString().trim()
            val namaBelakang = namaBelakangEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val sandi = passwordEditText.text.toString().trim()
            val role = "user"
            if (namaDepan.isEmpty() || namaBelakang.isEmpty() || email.isEmpty() || sandi.isEmpty()) {
                Toast.makeText(this, "Harap diisi semuanya", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                val newUser = User(
                    namaDepan = namaDepan,
                    namaBelakang = namaBelakang,
                    email = email,
                    sandi = sandi,
                    role = role
                )
                registerUser(newUser)
            }
        }
    }

    private fun registerUser(user: User) {
        val call = RetrofitClient.instance.register(user)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Registration Successful",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Navigate to LoginActivity
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("SignUpActivity", "Registration Failed: ${response.code()} - $errorBody")
                    Toast.makeText(
                        this@SignUpActivity,
                        "Registration Failed: ${response.code()} - $errorBody",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Error: ${t.message}", Toast.LENGTH_SHORT)
                    .show()
                Log.e("SignUpActivity", "Error: ${t.message}", t)
            }
        })
    }
}