package com.example.tutorialdiwithkotlin.networking

import com.example.tutorialdiwithkotlin.questions.QuestionWithBody
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by khoado on 05,February,2020
 */

class SingleQuestionResponseSchema(question : QuestionWithBody) {
    @SerializedName("items")
    private val mQuestions : List<QuestionWithBody> = Collections.singletonList(question)

    fun getQuestion() : QuestionWithBody {
        return mQuestions[0]
    }
 }