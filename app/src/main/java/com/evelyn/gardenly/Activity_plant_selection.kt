package com.evelyn.gardenly

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Activity_plant_selection : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "guest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_selection)

        val plantButtons = listOf(
            R.id.btnFruit to "Fruit Trees",
            R.id.btnFlower to "Flowers",
            R.id.btnOrnamentalPlants to "Ornamental Plants"
        )

        plantButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                val data = mapOf("plant" to value)
                db.collection("users").document(uid)
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(this, "Saved plant: $value", Toast.LENGTH_SHORT).show()
                        // Optional: chuyển sang HomeActivity
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
