package com.example.tutorialdiwithkotlin.networking

import com.example.tutorialdiwithkotlin.questions.Question
import com.google.gson.annotations.SerializedName

/**
 * Created by khoado on 05,February,2020
 */

class QuestionsListResponseSchema(question : List<Question>) {
    @SerializedName("items")
    private val mQuestions : List<Question> = question


    fun getQuestions() : List<Question> {
        return mQuestions
    }


}