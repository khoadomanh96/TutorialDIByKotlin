package com.example.tutorialdiwithkotlin.networking

import com.example.tutorialdiwithkotlin.questions.QuestionWithBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by khoado on 05,February,2020
 */

interface StackoverflowAPI {
    @GET("/questions?order=desc&sort=activity&site=stackoverflow")
    fun lastActiveQuestions(@Query("pagesize") pageSize : Int) : Call<QuestionsListResponseSchema>

    @GET("/questions/{questionId}?site=stackoverflow&filter=withbody")
    fun questionDetails(@Path("questionId") question : String) : Call<SingleQuestionResponseSchema>
}