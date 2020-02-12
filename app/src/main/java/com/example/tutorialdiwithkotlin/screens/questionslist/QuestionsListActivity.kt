package com.example.tutorialdiwithkotlin.screens.questionslist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorialdiwithkotlin.questions.FetchQuestionsListUseCase
import com.example.tutorialdiwithkotlin.questions.Question
import com.example.tutorialdiwithkotlin.screens.common.ServerErrorDialogFragment
import com.example.tutorialdiwithkotlin.screens.questiondetails.QuestionDetailsActivity

/**
 * Created by khoado on 06,February,2020
 */

class QuestionsListActivity : AppCompatActivity(),
    QuestionsListViewMvc.Listener, FetchQuestionsListUseCase.Listener {
    private val NUM_OF_QUESTIONS_TO_FETCH = 20

    private var mFetchQuestionsListUseCase : FetchQuestionsListUseCase?=null
    private var mViewMvc: QuestionsListViewMvc? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewMvc = QuestionListViewMvcImpl(LayoutInflater.from(this), null)

        setContentView((mViewMvc as QuestionListViewMvcImpl).getRootView())

        mFetchQuestionsListUseCase  = FetchQuestionsListUseCase()
    }

    override fun onStart() {
        super.onStart()
        mViewMvc?.registerListener(this)
        mFetchQuestionsListUseCase?.run {
            registerListener(this@QuestionsListActivity)
            fetchLastActiveQuestionsAndNotify(NUM_OF_QUESTIONS_TO_FETCH)
        }
    }

    override fun onStop() {
        super.onStop()
        mViewMvc?.unregisterListener(this)
        mFetchQuestionsListUseCase?.unregisterListener(this)
    }


    override fun onQuestionClicked(question: Question) {
        QuestionDetailsActivity.start(context = this@QuestionsListActivity,questionID = question.getId())
    }

    override fun onFetchOfQuestionDetailsSucceeded(question: List<Question>) {
        mViewMvc?.bindQuestions(question)
    }

    override fun onFetchOfQuestionDetailsFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

}