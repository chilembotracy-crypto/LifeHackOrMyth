package com.tracy.lifehackormyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    private val tag = "ScoreActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Score sent from QuestionActivity
        val score = intent.getIntExtra("SCORE", 0)
        val total = questionBank.size
        Log.d(tag, "Score screen loaded. Score: $score/$total")

        val (title, message) = when {
            score >= 9 -> "Master hacker!" to "You spotted most of the myths. Sharp instincts."
            score >= 5 -> "Getting there!" to "A decent run. Some myths slipped past you."
            else -> "Stay Safe Online!" to "Plenty of myths fooled you. Check before you trust a hack."
        }

        findViewById<TextView>(R.id.scoreCircle).text = "$score/$total"
        findViewById<TextView>(R.id.resultTitleText).text = title
        findViewById<TextView>(R.id.resultFeedbackText).text = message


        findViewById<Button>(R.id.reviewButton).setOnClickListener {
            Log.d(tag, "Review button clicked")
            startActivity(Intent(this, ReviewActivity::class.java))
        }
    }
}
