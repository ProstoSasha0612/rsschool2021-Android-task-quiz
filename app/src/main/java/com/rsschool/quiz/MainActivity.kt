package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity(),TransitInterface ,TransitToResults {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openQuizFragment()
        }
    }

    override fun openQuizFragment() {
        val fragment = QuestionFragment.newInstance()
        openFragment(fragment)
    }

    override fun openResultsFragment(
        correctAnswersCount: Int,
        answersCount: Int,
        questions: IntArray,
        answers: IntArray
    ) {
        val fragment =
            ResultFragment.newInstance(correctAnswersCount, answersCount, questions, answers)
        openFragment(fragment)
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun openCleanQuizFragment() { //здесь происходдит очистка всех данных ViewModel, в соответсвии с которыми строится фрагмент
        val fragment = QuestionFragment.newInstance()
        val quizVM: QuizViewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)
        quizVM.currentQuestion = 0
        for (i in 0 until quizVM.answersList.size) {
            quizVM.answersList[i] = QuizViewModel.NO_ANSWER
            openFragment(fragment)
        }
    }

}
