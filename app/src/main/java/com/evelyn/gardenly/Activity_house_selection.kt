package com.evelyn.gardenly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class Activity_house_selection : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "guest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_selection)

        val houseButtons = listOf(
            R.id.btnDetachedHouse to "House",
            R.id.btnApartment to "Apartment"
        )

        houseButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                val data = mapOf("houseType" to value)
                db.collection("users").document(uid)
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(this, "Saved house: $value", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Activity_plant_selection::class.java)
                        intent.putExtra("houseType", value)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
