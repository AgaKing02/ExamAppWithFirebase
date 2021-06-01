package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    companion object {
        var cheatCount = 0
    }

    private lateinit var questionTv: TextView
    private lateinit var trueBtn: Button
    private lateinit var falseBtn: Button
    private lateinit var nextBtn: Button
    private lateinit var showAnswerBtn: Button

    private lateinit var questions: Array<String>
    private lateinit var answers: Array<String>

    private val questions1 = mutableListOf<String>()
    private val answers1 = mutableListOf<String>()

    private lateinit var userAnswer: Array<Boolean>
    private val database = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = database.getReference("questions")

    private var i = 0

    private fun init() {
        questionTv = findViewById(R.id.question_text_view)
        trueBtn = findViewById(R.id.true_btn)
        falseBtn = findViewById(R.id.false_btn)
        nextBtn = findViewById(R.id.next_button)
        showAnswerBtn = findViewById(R.id.show_answer_button)
        userAnswer = Array(questions.size) { false }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myRef2 = database.getReference("message")


        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var size = 0
                for(snapshot in dataSnapshot.children) {
                    size++
                }
                questions = Array(size){""}
                answers = Array(size){""}
                var itr = 0
                for (snapshot in dataSnapshot.children) {
                    questions[itr] = snapshot.child("question").value.toString()
                    answers[itr] = snapshot.child("answer").value.toString()
                    println(snapshot.child("question").value.toString() + " " +snapshot.child("answer").value.toString())
                    itr++
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
            }
        }
        myRef.addValueEventListener(postListener)
        Handler().postDelayed({
            init()

            questionTv.text = questions[i]

            trueBtn.setOnClickListener {
                answer(true)
            }

            falseBtn.setOnClickListener {
                answer(false)
            }

            nextBtn.setOnClickListener {
                nextQuestion()
            }

            showAnswerBtn.setOnClickListener { showAnswer() }
        }, 5000)
    }



    private fun answer(answer: Boolean) {
        userAnswer[i] = answer.toString() == answers[i]
        nextBtn.visibility = View.VISIBLE
    }
    private fun nextQuestion() {
        if (i + 1 != questions.size) {
            i++
            questionTv.text = questions[i]
            nextBtn.visibility = View.INVISIBLE
        } else {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("quiz_result", userAnswer)
            startActivity(intent)
            finish()
        }
    }
    private fun showAnswer() {
        cheatCount++
        if (cheatCount > 2) {
            Toast.makeText(this, "You cannot cheat!", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(this, CheatingActivity::class.java)
        intent.putExtra("correct_answer", answers[i])
        startActivity(intent)
    }
}