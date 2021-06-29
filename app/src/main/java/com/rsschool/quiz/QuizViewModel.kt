package com.rsschool.quiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    val answersList = mutableListOf<Int>(NO_ANSWER, NO_ANSWER, NO_ANSWER, NO_ANSWER, NO_ANSWER)
    var currentQuestion = 0

    companion object {
        const val NO_ANSWER = -1
    }
}