package com.rsschool.quiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    val questionList = listOf<Question>(
        Question(
            R.string.question1,
            listOf<Int>(
                R.string.answer11,
                R.string.answer12,
                R.string.answer13,
                R.string.answer14,
                R.string.answer15
            ),
            R.string.answer11
        ),
        Question(
            R.string.question2,
            listOf<Int>(
                R.string.answer21,
                R.string.answer22,
                R.string.answer23,
                R.string.answer24,
                R.string.answer25
            ),
            R.string.answer22
        ),
        Question(
            R.string.question3,
            listOf<Int>(
                R.string.answer31,
                R.string.answer32,
                R.string.answer33,
                R.string.answer34,
                R.string.answer35
            ),
            R.string.answer33
        ),
        Question(
            R.string.question4, listOf<Int>(
                R.string.answer41,
                R.string.answer42,
                R.string.answer43,
                R.string.answer44,
                R.string.answer45
            ),
            R.string.answer44
        ),
        Question(
            R.string.question5,
            listOf<Int>(
                R.string.answer51,
                R.string.answer52,
                R.string.answer53,
                R.string.answer54,
                R.string.answer55
            ),
            R.string.answer55
        )
    )

    val answersList = mutableListOf<Int>(NO_ANSWER, NO_ANSWER, NO_ANSWER, NO_ANSWER, NO_ANSWER)
    var currentQuestion = 0

    companion object {
        const val NO_ANSWER = -1
    }
}