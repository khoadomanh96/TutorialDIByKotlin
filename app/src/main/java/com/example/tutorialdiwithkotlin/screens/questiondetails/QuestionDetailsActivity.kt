package com.example.tutorialdiwithkotlin.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorialdiwithkotlin.questions.FetchQuestionDetailsUsecase
import com.example.tutorialdiwithkotlin.questions.QuestionWithBody
import com.example.tutorialdiwithkotlin.screens.common.ServerErrorDialogFragment

/**
 * Created by khoado on 06,February,2020
 */

class QuestionDetailsActivity : AppCompatActivity(),
    QuestionDetailsViewMvc.Listener,
    FetchQuestionDetailsUsecase.Listener {

    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"

        fun start(context: Context, questionID: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionID)
            context.startActivity(intent)
        }
    }

    private var mFetchQuestionDetailsUsecase: FetchQuestionDetailsUsecase? = null
    private var mQuestionId: String? = null
    private var mViewMvc: QuestionDetailsViewMvc? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewMvc = QuestionDetailsViewMvcImpl(LayoutInflater.from(this), null)

        setContentView((mViewMvc as QuestionDetailsViewMvcImpl).getRootView())

        mFetchQuestionDetailsUsecase = FetchQuestionDetailsUsecase()

        mQuestionId = intent.extras?.getString(EXTRA_QUESTION_ID) ?: ""

    }

    override fun onStart() {
        super.onStart()
        mViewMvc?.registerListener(this@QuestionDetailsActivity)
        mFetchQuestionDetailsUsecase?.run {
            registerListener(this@QuestionDetailsActivity)
            fetchQuestionDetailsAndNotify(mQuestionId ?: "")
        }
    }

    override fun onStop() {
        super.onStop()
        mViewMvc?.unregisterListener(this@QuestionDetailsActivity)
        mFetchQuestionDetailsUsecase?.unregisterListener(this)
    }


    override fun onFetchOfQuestionDetailsSucceeded(question: QuestionWithBody) {
        mViewMvc?.bindQuestion(question)
    }

    override fun onFetchOfQuestionDetailsFailed() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

}