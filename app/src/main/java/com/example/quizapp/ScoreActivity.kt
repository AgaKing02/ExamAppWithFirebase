package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    private lateinit var resultTv: TextView

    private fun init() {
        resultTv = findViewById(R.id.quiz_result_text_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        init()

        val results:Array<Boolean> = intent.getSerializableExtra("quiz_result") as Array<Boolean>

        var s = ""
        for(i in results.indices){
            s+="${i+1}. ${results[i]}\n"
        }
        resultTv.text = s
    }
}