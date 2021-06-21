package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rsschool.quiz.databinding.FragmentResultBinding
import java.lang.StringBuilder


class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val correctAnswersCount = arguments?.getInt(CORRECT_ANSWERS_COUNT)?:0
        val answersCount = arguments?.getInt(ANSWERS_COUNT)?:0
        val questions = arguments?.getIntArray(QUESTIONS)
        val answers = arguments?.getIntArray(ANSWERS)


        binding.resultTv.text = "Ваш результат $correctAnswersCount из $answersCount!"
        binding.exitBtn.setOnClickListener{
            requireActivity().finish()
        }
        binding.backBtn.setOnClickListener{
            val transit = context as TransitInterface
            transit.openCleanQuizFragment()
        }

        val messageToShare = StringBuilder("QUIZ RESULT: $correctAnswersCount of $answersCount!\n")
        for(i in 0 until answersCount){
            messageToShare.append("${i+1}) ${resources.getString(questions?.get(i)?:0)} \n" +
                    " ${resources.getString(answers?.get(i) ?: 0)} \n")
        }
        binding.shareBtn.setOnClickListener{
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, messageToShare.toString())
            }
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(
            correctAnswersCount: Int,
            answersCount: Int,
            questions: IntArray,
            answers: IntArray
        ): ResultFragment {
            return ResultFragment().apply {
                arguments = bundleOf(
                    CORRECT_ANSWERS_COUNT to correctAnswersCount,
                    ANSWERS_COUNT to answersCount,
                    QUESTIONS to questions,
                    ANSWERS to answers
                )
            }
        }
        private const val CORRECT_ANSWERS_COUNT = "Correct answers count"
        private const val ANSWERS_COUNT = "Answers count"
        private const val QUESTIONS = "Questions"
        private const val ANSWERS = "Answers"
        private const val RESULTS = "Results"
    }
}