package com.evelyn.gardenly

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class Activity_plant_selection : AppCompatActivity() {

    private var selectedClimate: String? = null
    private var selectedHouseType: String? = null
    private var selectedPlantType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_selection)

        selectedClimate = intent.getStringExtra("climate")
        selectedHouseType = intent.getStringExtra("house_type")

        val plantButtons = listOf(
            R.id.btnFruit to "Fruit Trees",
            R.id.btnFlower to "Flowers",
            R.id.btnOrnamentalPlants to "Ornamental Plants"
        )

        plantButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                selectedPlantType = value
                saveToFirestore()
            }
        }
    }

    private fun saveToFirestore() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "Please log in", Toast.LENGTH_SHORT).show()
            return
        }

        val data = hashMapOf(
            "climate" to selectedClimate,
            "house_type" to selectedHouseType,
            "plant_preference" to selectedPlantType,
            "timestamp" to FieldValue.serverTimestamp()
        )

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(userId)
            .collection("conditions")
            .document("living")
            .set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Could not save data", Toast.LENGTH_SHORT).show()
            }
    }
}
