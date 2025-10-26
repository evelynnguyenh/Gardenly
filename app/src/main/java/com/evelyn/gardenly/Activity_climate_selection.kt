package com.evelyn.gardenly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Activity_climate_selection : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "guest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_climate_selection)

        val climateButtons = listOf(
            R.id.btnTropical to "Tropical",
            R.id.btnSubtropical to "Subtropical",
            R.id.btnContinental to "Continental",
            R.id.btnMediterranean to "Mediterranean",
            R.id.btnTemperate to "Temperate",
            R.id.btnOther to "Other"
        )

        climateButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                val data = mapOf("climate" to value)
                db.collection("users").document(uid)
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(this, "Saved climate: $value", Toast.LENGTH_SHORT).show()


                        val intent = Intent(this, Activity_house_selection::class.java)
                        intent.putExtra("climate", value)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
