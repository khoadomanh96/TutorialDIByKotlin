package com.example.tutorialdiwithkotlin.screens.questiondetails

import com.example.tutorialdiwithkotlin.questions.QuestionWithBody
import com.example.tutorialdiwithkotlin.screens.common.mvcviews.ObservableViewMvc

/**
 * Created by khoado on 06,February,2020
 */
interface QuestionDetailsViewMvc : ObservableViewMvc<QuestionDetailsViewMvc.Listener> {

    interface Listener {

    }

    fun bindQuestion(question : QuestionWithBody)
}