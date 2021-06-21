package com.rsschool.quiz

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.rsschool.quiz.databinding.FragmentQuizBinding


class QuestionFragment : Fragment(){

    private var _binding:FragmentQuizBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val quizVM:QuizViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(QuizViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(quizVM.currentQuestion!= 0) {
            requireActivity().onBackPressedDispatcher.addCallback(this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        backClick()
                    }
                })
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.setTheme(ThemesDataBase.themes[quizVM.currentQuestion%5])
        _binding = FragmentQuizBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStatusBarColor()
        setTitle()
        setQuestion()
        setAnswers()
        setButtonsEnabled()
        setPastAnswer()
        setListeners()
    }


    override fun onDestroyView() {
        // nulling a variable _binding!!!
        _binding = null
        super.onDestroyView()
    }

    private fun setTitle(){
        binding.toolbar.title = "Question ${quizVM.currentQuestion + 1}"
    }

    private fun setQuestion(){
        binding.question.text = resources.getText(quizVM.questionList[quizVM.currentQuestion].title)
    }

    private fun setAnswers(){
        binding.optionOne.text = resources.getText(quizVM.questionList[quizVM.currentQuestion].answers[0])
        binding.optionTwo.text = resources.getText(quizVM.questionList[quizVM.currentQuestion].answers[1])
        binding.optionThree.text = resources.getText(quizVM.questionList[quizVM.currentQuestion].answers[2])
        binding.optionFour.text = resources.getText(quizVM.questionList[quizVM.currentQuestion].answers[3])
        binding.optionFive.text = resources.getText(quizVM.questionList[quizVM.currentQuestion].answers[4])
    }

    private fun setButtonsEnabled(){
        if(quizVM.currentQuestion== 0){
            binding.previousButton.isEnabled = false
        }
        else binding.previousButton.isEnabled = true


        binding.nextButton.isEnabled = quizVM.answersList[quizVM.currentQuestion] != QuizViewModel.NO_ANSWER

        makeSubmitBtn()
    }

    private fun makeSubmitBtn(){
        if(quizVM.currentQuestion == 4){
            binding.nextButton.text = "Submit"
            binding.nextButton.setOnClickListener {
                val transit = requireActivity() as TransitToResults
                var correctAnswersCount =
                    0                     //считаем количество правильных ответов
                for (i in 0 until quizVM.answersList.size) {
                    if (quizVM.questionList[i].answers[quizVM.answersList[i]] == quizVM.questionList[i].correctAnswer) { //проверяем ответ пользователя, индекс которого находится в answersList
                        correctAnswersCount++
                    }
                }
                val questions = IntArray(quizVM.questionList.size){-1} //создаем лист со всеми вопросами для того, чтобы потом имми можно было поделиться
                for (i in quizVM.questionList.indices) {
                    questions[i] = (quizVM.questionList[i].title)
                }
                val answers = IntArray(quizVM.questionList.size){-1}//arrayOf<Int>(quizVM.questionList.size) //создаем лист со всеми ответами пользователя для того, чтобы потом имми можно было поделиться
                for (i in quizVM.questionList.indices) {
                    answers[i] = (quizVM.questionList[i].answers[quizVM.answersList[i]]) //добавляем ответ пользователя, индекс которого находится в answersList
                }
                transit.openResultsFragment(
                    correctAnswersCount,
                    quizVM.questionList.size,
                    questions,
                    answers
                )
            }
        }
    }

    private fun setPastAnswer(){
        if(quizVM.answersList[quizVM.currentQuestion] != QuizViewModel.NO_ANSWER){
            when(quizVM.answersList[quizVM.currentQuestion]){
                0 -> binding.optionOne.isChecked = true
                1 -> binding.optionTwo.isChecked = true
                2 -> binding.optionThree.isChecked = true
                3 -> binding.optionFour.isChecked =  true
                4 -> binding.optionFive.isChecked =  true
            }
        }
    }


    private fun setListeners(){
        val transit = activity as TransitInterface
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            setButtonsEnabled()
            binding.nextButton.isEnabled = true

            val index = when(binding.radioGroup.checkedRadioButtonId){
                binding.optionOne.id -> 0
                binding.optionTwo.id -> 1
                binding.optionThree.id -> 2
                binding.optionFour.id -> 3
                else -> 4
            }
            quizVM.answersList[quizVM.currentQuestion] = index

        }
        binding.nextButton.setOnClickListener {
            quizVM.currentQuestion++
            transit.openQuizFragment()
        }
        binding.previousButton.setOnClickListener {
            backClick()

        }
        binding.toolbar.setNavigationOnClickListener {
            backClick()
        }
    }

    private fun backClick(){
        val transit = activity as TransitInterface
        if(quizVM.currentQuestion != 0) {
            quizVM.currentQuestion--
            transit.openQuizFragment()
        }
    }

    private fun setStatusBarColor(){
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
        val color = typedValue.data
        activity?.window?.statusBarColor = color
    }


    companion object{
        @JvmStatic
        fun newInstance():QuestionFragment {
            return QuestionFragment()
        }
    }
}
