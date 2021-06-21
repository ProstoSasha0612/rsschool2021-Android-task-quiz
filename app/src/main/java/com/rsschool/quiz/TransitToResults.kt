package com.rsschool.quiz

interface TransitToResults {
    fun openResultsFragment(
        correctAnswersCount: Int,
        answersCount: Int,
        questions: IntArray,
        answers: IntArray
    )
}