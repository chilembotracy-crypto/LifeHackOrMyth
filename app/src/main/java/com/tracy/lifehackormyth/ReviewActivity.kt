/*
 * ReviewActivity.kt
 * Lists all 12 statements with the correct answer and explanation so the
 * user can learn from their mistakes.
 *
 * References:
 * Android Developers (n.d.) LinearLayout. Available at:
 * https://developer.android.com/reference/android/widget/LinearLayout
 * (Accessed: 3 October 2026).
 * Android Developers (n.d.) ScrollView. Available at:
 * https://developer.android.com/reference/android/widget/ScrollView
 * (Accessed: 3 October 2026).
 */
package com.tracy.lifehackormyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ReviewActivity", "Review screen loaded")

        // Convert 16dp to pixels so spacing looks the same on every screen
        val pad = (16 * resources.displayMetrics.density).toInt()

        // Vertical layout that holds every review entry (built in code, not XML)
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad * 3, pad, pad)
        }

        // Add one entry per question: statement, correct answer, explanation
        questionBank.forEachIndexed { i, q ->
            // Show HACK or MYTH from the stored answer
            val verdict = if (q.isHack) "HACK" else "MYTH"
            container.addView(TextView(this).apply {
                text = "${i + 1}. ${q.statement}\nAnswer: $verdict\n${q.explanation}"
                textSize = 15f
                setPadding(0, 0, 0, pad)
            })
        }

        // Button that takes the user back to the Welcome screen
        container.addView(Button(this).apply {
            text = "Back to start"
            setOnClickListener {
                startActivity(Intent(this@ReviewActivity, MainActivity::class.java))
                finish()
            }
        })

        // ScrollView lets the user scroll through all 12 entries
        setContentView(ScrollView(this).apply { addView(container) })
    }
}