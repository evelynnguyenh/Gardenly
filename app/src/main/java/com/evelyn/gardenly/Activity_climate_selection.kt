package com.evelyn.gardenly

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Activity_climate_selection: AppCompatActivity() {

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
                // Khi chọn xong khí hậu → chuyển sang HouseSelectionActivity
                val intent = Intent(this, Activity_house_selection::class.java)
                intent.putExtra("climate", value)
                startActivity(intent)
            }
        }
    }
}
