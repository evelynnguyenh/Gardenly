package com.evelyn.gardenly

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // layout for login screen (res/layout/login.xml)

        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val continueButton = findViewById<TextView>(R.id.continueButton)
        val continueGGButton = findViewById<TextView>(R.id.continueGGButton)

        // Continue → go to ActivityClimateSelection
        continueButton.setOnClickListener {
            val phone = phoneEditText.text.toString().trim()
            if (phone.isEmpty()) {
                Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, activity_climate_selection::class.java)
                startActivity(intent)
            }
        }

        // Continue with Google → just a placeholder
        continueGGButton.setOnClickListener {
            Toast.makeText(this, "Google Sign-In clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
