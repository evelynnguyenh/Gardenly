package com.evelyn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.evelyn.gardenly.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnStartSurvey).setOnClickListener {
            val intent = Intent(this, LivingConditionsActivity::class.java)
            startActivity(intent)
        }
    }
}