/*
 * QuestionActivity.kt
 * Shows the 12 flashcards one at a time, checks each answer, gives feedback,
 * keeps the score, and sends the final score to ScoreActivity.
 *
 * References:
 * Android Developers (n.d.) Introduction to activities. Available at:
 * https://developer.android.com/guide/components/activities/intro-activities
 * (Accessed: 3 October 2026).
 * Android Developers (n.d.) Intents and intent filters. Available at:
 * https://developer.android.com/guide/components/intents-filters
 * (Accessed: 3 October 2026).
 * Kotlin (n.d.) Data classes. Available at:
 * https://kotlinlang.org/docs/data-classes.html (Accessed: 3 October 2026).
 */
package com.tracy.lifehackormyth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// One flashcard: the claim, whether it is a real hack, and why
data class Question(val statement: String, val isHack: Boolean, val explanation: String)

// Top-level list so ScoreActivity and ReviewActivity can reuse the same questions
val questionBank = listOf(
    Question("\"Putting your phone in rice will fix water damage.\"", false, "Rice barely pulls moisture out. Switch the phone off and get it checked."),
    Question("\"Closing background apps saves lots of battery.\"", false, "Reopening apps uses more power than leaving them paused."),
    Question("\"A wooden spoon over a pot stops it boiling over.\"", true, "The spoon pops bubbles as they reach the rim."),
    Question("\"Cracking your knuckles causes arthritis.\"", false, "Studies have found no link."),
    Question("\"Wrapping banana stems in cling film slows ripening.\"", true, "It traps the ethylene gas the stem releases."),
    Question("\"Charging overnight ruins your phone battery.\"", false, "Modern phones stop charging when full."),
    Question("\"Rubbing a walnut on scratched wood hides the scratch.\"", true, "The natural oils darken the scratch."),
    Question("\"Reading in dim light permanently damages your eyes.\"", false, "It causes tiredness, not lasting damage."),
    Question("\"A slice of bread softens hard brown sugar.\"", true, "The sugar absorbs moisture from the bread."),
    Question("\"Incognito mode makes you invisible online.\"", false, "Websites and your network can still see you."),
    Question("\"Freezing chewing gum makes it easy to remove from fabric.\"", true, "Frozen gum goes brittle and flakes off."),
    Question("\"You only use 10% of your brain.\"", false, "Brain scans show we use virtually all of it.")
)

class QuestionActivity : AppCompatActivity() {

    private val tag = "QuestionActivity" // Tag used to filter messages in Logcat
    private var index = 0               // Which question is on screen
    private var score = 0               // Number of correct answers so far

    // Views are connected in onCreate, so lateinit is used
    private lateinit var caseNumberText: TextView
    private lateinit var scoreText: TextView
    private lateinit var statementText: TextView
    private lateinit var feedbackText: TextView
    private lateinit var hackButton: Button
    private lateinit var mythButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        // Connect each variable to its view in activity_question.xml
        caseNumberText = findViewById(R.id.caseNumberText)
        scoreText = findViewById(R.id.scoreText)
        statementText = findViewById(R.id.statementText)
        feedbackText = findViewById(R.id.feedbackText)
        hackButton = findViewById(R.id.hackButton)
        mythButton = findViewById(R.id.mythButton)
        nextButton = findViewById(R.id.nextButton)

        // Each button calls the matching function when tapped
        hackButton.setOnClickListener { checkAnswer(true) }
        mythButton.setOnClickListener { checkAnswer(false) }
        nextButton.setOnClickListener { goToNext() }

        Log.d(tag, "Question screen loaded")
        showQuestion()
    }

    // Displays the current question and resets the buttons for a new answer
    private fun showQuestion() {
        val q = questionBank[index]
        caseNumberText.text = "Case ${index + 1}/${questionBank.size}"
        scoreText.text = "Score: $score"
        statementText.text = q.statement
        feedbackText.text = ""
        hackButton.isEnabled = true
        mythButton.isEnabled = true
        // Next stays disabled until the user answers
        nextButton.isEnabled = false
        // The last question changes the button text to lead to the results
        nextButton.text = if (index == questionBank.size - 1) "See results" else "Next case"
        Log.d(tag, "Showing question ${index + 1}")
    }

    // Compares the user's choice with the correct answer and updates the score
    private fun checkAnswer(userSaidHack: Boolean) {
        val q = questionBank[index]
        // Lock buttons so the score can't be counted twice
        hackButton.isEnabled = false
        mythButton.isEnabled = false

        if (userSaidHack == q.isHack) {
            score++
            feedbackText.text = "Correct! ${q.explanation}"
            Log.d(tag, "Correct answer. Score is now $score")
        } else {
            feedbackText.text = "Not quite. ${q.explanation}"
            Log.d(tag, "Wrong answer. Score stays $score")
        }
        scoreText.text = "Score: $score"
        nextButton.isEnabled = true
    }

    // Moves to the next question, or to the score screen after the last one
    private fun goToNext() {
        if (index < questionBank.size - 1) {
            index++
            showQuestion()
        } else {
            Log.d(tag, "Quiz finished. Final score $score")
            // Pass the score to ScoreActivity using an Intent extra
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
        }
    }
}
