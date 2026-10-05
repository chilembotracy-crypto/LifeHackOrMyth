/*
 * ScoreActivity.kt
 * Shows the final score with personalised feedback and a button to review
 * every statement.
 *
 * References:
 * Android Developers (n.d.) Intents and intent filters. Available at:
 * https://developer.android.com/guide/components/intents-filters
 * (Accessed: 3 October 2026).
 * Kotlin (n.d.) Conditions and loops (when expression). Available at:
 * https://kotlinlang.org/docs/control-flow.html (Accessed: 3 October 2026).
 */
package com.tracy.lifehackormyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    private val tag = "ScoreActivity" // Tag used to filter messages in Logcat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Read the score sent from QuestionActivity (0 if missing)
        val score = intent.getIntExtra("SCORE", 0)
        // Total questions, taken from the shared question list
        val total = questionBank.size
        Log.d(tag, "Score screen loaded. Score: $score/$total")

        // Pick personalised feedback based on the score range
        val (title, message) = when {
            score >= 9 -> "Master hacker!" to "You spotted most of the myths. Sharp instincts."
            score >= 5 -> "Getting there!" to "A decent run. Some myths slipped past you."
            else -> "Stay Safe Online!" to "Plenty of myths fooled you. Check before you trust a hack."
        }

        // Show the score and feedback on screen
        findViewById<TextView>(R.id.scoreCircle).text = "$score/$total"
        findViewById<TextView>(R.id.resultTitleText).text = title
        findViewById<TextView>(R.id.resultFeedbackText).text = message

        // Review button opens ReviewActivity with every statement and answer
        findViewById<Button>(R.id.reviewButton).setOnClickListener {
            Log.d(tag, "Review button clicked")
            startActivity(Intent(this, ReviewActivity::class.java))
        }
    }
}
