package com.example.tutorialdiwithkotlin.questions

import com.google.gson.annotations.SerializedName

/**
 * Created by khoado on 05,February,2020
 */

class QuestionWithBody(title : String, id : String, body : String) {
    @SerializedName("title")
    private val mTitle : String = title
    @SerializedName("question_id")
    private val mId = id
    @SerializedName("body")
    private val mBody = body


    fun getTitle() : String  {
        return mTitle
    }
    fun getId() : String {
        return mId
    }
    fun getBody() : String {
        return mBody
    }
}