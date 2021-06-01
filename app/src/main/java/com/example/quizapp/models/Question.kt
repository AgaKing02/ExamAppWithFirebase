package com.example.quizapp.models

class Question {
    var id: String = ""
    var question: String = ""
    var answer: Boolean = true

    fun setAnswer(id: String, question: String, answer: Boolean) {
        this.id = id
        this.question = question
        this.answer = answer
    }


}