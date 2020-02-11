package com.example.tutorialdiwithkotlin.screens.questionslist

import com.example.tutorialdiwithkotlin.questions.Question
import com.example.tutorialdiwithkotlin.screens.common.mvcviews.ObservableViewMvc

/**
 * Created by khoado on 06,February,2020
 */

interface QuestionsListViewMvc : ObservableViewMvc<QuestionsListViewMvc.Listener> {
    interface Listener {
        fun onQuestionClicked(question : Question)
    }

    fun bindQuestions(question : List<Question>)

}