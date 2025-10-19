package com.evelyn.gardenly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Activity_house_selection : AppCompatActivity() {

    private var selectedHouseType: String? = null
    private var selectedClimate: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_selection)


        selectedClimate = intent.getStringExtra("climate")

        val houseButtons = listOf(
            R.id.btnApartment to "Apartment",
            R.id.btnDetachedHouse to "House"
        )

        houseButtons.forEach { (id, value) ->
            findViewById<Button>(id).setOnClickListener {
                selectedHouseType = value


                val intent = Intent(this, Activity_plant_selection::class.java)
                intent.putExtra("climate", selectedClimate)
                intent.putExtra("house_type", selectedHouseType)
                startActivity(intent)
            }
        }
    }
}
