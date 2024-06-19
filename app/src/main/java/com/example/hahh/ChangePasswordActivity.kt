package com.example.hahh

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var changePasswordButton: Button
    val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val id = sharedPreferences.getInt("id",-1)
    val email = sharedPreferences.getString("email", "emailuser")
    val role = sharedPreferences.getString("role", "roleuser")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        oldPasswordEditText = findViewById(R.id.input_sandi_lama)
        newPasswordEditText = findViewById(R.id.input_sandi_baru)
        changePasswordButton = findViewById(R.id.button)

        changePasswordButton.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim()

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Sandi lama dan sandi baru harus diisi", Toast.LENGTH_SHORT).show()
            } else {
                val changePasswordRequest = ForgetPass(newPass = newPassword, confirmPass = newPassword)
                changePassword(oldPassword, changePasswordRequest)
            }
        }
    }

    private fun changePassword(oldPassword: String, request: ForgetPass) {
        // Retrieve user id or any necessary data here to pass to API if needed
        val userId = id // Replace with actual user id or retrieve dynamically

        APIAuth.instance.forgetPassword(userId, request).enqueue(object : Callback<ForgetPass> {
            override fun onResponse(call: Call<ForgetPass>, response: Response<ForgetPass>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ChangePasswordActivity, "Sandi berhasil diubah", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(this@ChangePasswordActivity, "Gagal mengubah sandi: ${response.code()} - $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ForgetPass>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@ChangePasswordActivity, "Gagal mengubah sandi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
