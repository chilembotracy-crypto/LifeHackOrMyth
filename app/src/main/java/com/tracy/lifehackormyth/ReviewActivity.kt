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

        val pad = (16 * resources.displayMetrics.density).toInt()
        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad * 3, pad, pad)
        }

        questionBank.forEachIndexed { i, q ->
            val verdict = if (q.isHack) "HACK" else "MYTH"
            container.addView(TextView(this).apply {
                text = "${i + 1}. ${q.statement}\nAnswer: $verdict\n${q.explanation}"
                textSize = 15f
                setPadding(0, 0, 0, pad)
            })
        }

        container.addView(Button(this).apply {
            text = "Back to start"
            setOnClickListener {
                startActivity(Intent(this@ReviewActivity, MainActivity::class.java))
                finish()
            }
        })

        setContentView(ScrollView(this).apply { addView(container) })
    }
}
