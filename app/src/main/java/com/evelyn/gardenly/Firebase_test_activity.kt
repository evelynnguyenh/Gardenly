package com.evelyn.gardenly

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue

class FirebaseTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_test)

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()


        auth.signInAnonymously()
            .addOnSuccessListener {
                val userId = auth.currentUser?.uid
                Toast.makeText(this, "Auth success: $userId", Toast.LENGTH_SHORT).show()


                val data = hashMapOf(
                    "climate" to "Tropical",
                    "house_type" to "Apartment",
                    "plant_preference" to "Flowers",
                    "timestamp" to FieldValue.serverTimestamp()
                )

                db.collection("users").document(userId!!)
                    .collection("conditions").document("living")
                    .set(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Firestore saved!", Toast.LENGTH_SHORT).show()
                        Log.d("Firebase", "Saved successfully")


                        db.collection("users").document(userId)
                            .collection("conditions").document("living")
                            .get()
                            .addOnSuccessListener { doc ->
                                val climate = doc.getString("climate")
                                Log.d("Firebase", "Read success: climate=$climate")
                                Toast.makeText(this, "✅ Read success: $climate", Toast.LENGTH_SHORT).show()
                            }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Firestore save failed", Toast.LENGTH_SHORT).show()
                        Log.e("Firebase", "Error saving: ${it.message}")
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Auth failed", Toast.LENGTH_SHORT).show()
            }
    }
}
