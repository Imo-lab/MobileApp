package com.example.hahh

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(){
    private lateinit var namaDepanEditText: EditText
    private lateinit var namaBelakangEditText: EditText
    private lateinit var emailEditText: EditText
    private  lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button

    override  fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        namaDepanEditText = findViewById(R.id.input_first_name)
        namaBelakangEditText = findViewById(R.id.input_last_name)
        emailEditText = findViewById(R.id.input_email)
        passwordEditText = findViewById(R.id.input_sandi)
        signUpButton = findViewById(R.id.button)

        signUpButton.setOnClickListener{
            val namaDepan = namaDepanEditText.text.toString().trim()
            val namaBelakang = namaBelakangEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if(namaDepan.isEmpty() || namaBelakang.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Harap diisi semuanya", Toast.LENGTH_SHORT).show()
            }else {
                val newUser = User(namaDepan = namaDepan, namaBelakang = namaBelakang, email = email, password = password)
                registerUser(newUser)
            }
        }
    }

    private fun registerUser(user: User) {
        RetrofitClient.instance.register(user).enqueue(object: Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val registeredUser = response.body()
                    Log.d("SignUpActivity", "Registered user: $registeredUser")
                    Toast.makeText(this@SignUpActivity, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("SignUpActivity", "Registrasi gagal: ${response.message()}")
                    Toast.makeText(this@SignUpActivity, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@SignUpActivity,"Register gagal: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}