package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class CheatingActivity : AppCompatActivity() {
    private lateinit var answerTv: TextView

    private fun init(){
        answerTv = findViewById(R.id.answer_text_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        init()
        showAnswer()
    }

    private fun showAnswer(){
        val correctAnswer = intent.getStringExtra("correct_answer")
        answerTv.text = correctAnswer
        Toast.makeText(this, resources.getString(R.string.judgment_toast), Toast.LENGTH_LONG).show()
    }
}