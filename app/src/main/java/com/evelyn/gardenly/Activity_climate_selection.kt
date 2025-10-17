package com.evelyn.gardenly

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.evelyn.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class activity_climate_selection : AppCompatActivity() {

    // Step containers
    private lateinit var stepClimate: LinearLayout
    private lateinit var stepHouseType: LinearLayout
    private lateinit var stepInterest: LinearLayout

    // User selections
    private var selectedClimate: String? = null
    private var selectedHouseType: String? = null
    private var selectedPlantType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_climate_selection)

        // Initialize step layouts
        stepClimate = findViewById(R.id.stepClimate)
        stepHouseType = findViewById(R.id.stepHouseType)
        stepInterest = findViewById(R.id.stepInterest)

        setupClimateButtons()
        setupHouseButtons()
        setupPlantButtons()
    }

    private fun setupClimateButtons() {
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
                selectedClimate = value
                stepClimate.visibility = View.GONE
                stepHouseType.visibility = View.VISIBLE
            }
        }
    }

    private fun setupHouseButtons() {
        val houseButtons = listOf(
            R.id.btnApartment to "Apartment",
            R.id.btnDetachedHouse to "House"
        )

        houseButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                selectedHouseType = value
                stepHouseType.visibility = View.GONE
                stepInterest.visibility = View.VISIBLE
            }
        }
    }

    private fun setupPlantButtons() {
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