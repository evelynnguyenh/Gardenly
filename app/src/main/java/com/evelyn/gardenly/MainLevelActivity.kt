package com.evelyn.gardenly

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainLevelActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_lv)

        db = FirebaseFirestore.getInstance()

        // Lấy 6 circle từ layout
        val levels = listOf(
            R.id.circle1 to 1,
            R.id.circle2 to 2,
            R.id.circle3 to 3,
            R.id.circle4 to 4,
            R.id.circle5 to 5,
            R.id.circle6 to 6
        )

        levels.forEach { (id, levelNumber) ->
            findViewById<ImageView>(id).setOnClickListener {
                // Khi bấm 1 level → gọi hàm generateChallenge()
                generateChallenge(levelNumber)
            }
        }
    }

    // ⚙️ Sinh thử thách tương ứng với level
    private fun generateChallenge(level: Int) {
        val challengeRef = db.collection("challenges").document("level$level")

        challengeRef.get().addOnSuccessListener { doc ->
            if (doc.exists()) {
                val question = doc.getString("question")
                val hint = doc.getString("hint")
                val answer = doc.getString("answer")

                // Gửi sang Activity thử thách
                val intent = Intent(this, Level_page::class.java)
                intent.putExtra("level", level)
                intent.putExtra("question", question)
                intent.putExtra("hint", hint)
                intent.putExtra("answer", answer)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Chưa có thử thách cho level $level", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Lỗi tải thử thách: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
