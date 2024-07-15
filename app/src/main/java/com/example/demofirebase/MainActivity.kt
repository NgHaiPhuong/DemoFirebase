package com.example.demofirebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.demofirebase.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import kotlin.math.atan

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var databaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        databaseRef = FirebaseDatabase.getInstance().reference

        binding.btnSave.setOnClickListener {
            val id = "NV002" // Mã nhân viên, có thể tự động tạo hoặc dựa vào dữ liệu nhập từ người dùng
            val name = binding.etName.text.toString().trim()
            val password = binding.etMK.text.toString().trim()

            if (name.isNotEmpty() && password.isNotEmpty()) {
                saveUserToDatabase(id, name, password)
            } else {
                Toast.makeText(this, "Please enter name and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserToDatabase(id: String, name: String, password: String) {
        // Tạo một HashMap để lưu dữ liệu người dùng
        val userMap = HashMap<String, Any>()
        userMap["name"] = name
        userMap["password"] = password

        // Thêm dữ liệu vào Firebase Realtime Database
        databaseRef.child("users").child(id).setValue(userMap)
            .addOnSuccessListener {
                Toast.makeText(this, "User added to Firebase Database!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error adding user to Firebase Database: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}