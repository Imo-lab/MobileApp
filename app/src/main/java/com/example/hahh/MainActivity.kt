package com.example.hahh

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_penyelidikan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Example IDs
        val userIdToUpdate = 1
        val userIdToDelete = 1

        // Create a new user
        val newUser = User(namaDepan, namaBelakang, email, password)
        createUser(newUser)

        // Read users
        getUsers()

        // Update user with dynamic ID
        val updatedUser = User(id = userIdToUpdate, name = "Jane Doe", email = "jane.doe@example.com")
        updateUser(userIdToUpdate, updatedUser)

        // Delete user with dynamic ID
        deleteUser(userIdToDelete)
    }

    private fun createUser(user: User) {
        RetrofitClient.instance.createUser(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val createdUser = response.body()
                    Log.d("MainActivity", "Created user: $createdUser")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun getUsers() {
        RetrofitClient.instance.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    Log.d("MainActivity", "Users: $users")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun updateUser(id: Int, user: User) {
        RetrofitClient.instance.updateUser(id, user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val updatedUser = response.body()
                    Log.d("MainActivity", "Updated user: $updatedUser")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun deleteUser(id: Int) {
        RetrofitClient.instance.deleteUser(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "Deleted user with ID: $id")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}
