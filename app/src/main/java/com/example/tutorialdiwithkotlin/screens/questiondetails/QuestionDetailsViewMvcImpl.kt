package com.example.tutorialdiwithkotlin.screens.questiondetails

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.tutorialdiwithkotlin.R
import com.example.tutorialdiwithkotlin.questions.QuestionWithBody
import com.example.tutorialdiwithkotlin.screens.common.mvcviews.BaseViewMvc

/**
 * Created by khoado on 06,February,2020
 */

class QuestionDetailsViewMvcImpl(
    inflater: LayoutInflater,
    container: ViewGroup?
) : BaseViewMvc<QuestionDetailsViewMvc.Listener>(), QuestionDetailsViewMvc {


    private var mTxtQuestionBody: TextView? = null

    init {
        setRootView(inflater.inflate(R.layout.layout_question_details, container, false))
        mTxtQuestionBody = findViewById(R.id.txt_question_body)
    }

    override fun bindQuestion(question: QuestionWithBody) {
        val questionBody = question.getBody()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mTxtQuestionBody?.text = Html.fromHtml(questionBody, Html.FROM_HTML_MODE_LEGACY)
        } else {
            mTxtQuestionBody?.text = Html.fromHtml(questionBody)
        }
    }


}