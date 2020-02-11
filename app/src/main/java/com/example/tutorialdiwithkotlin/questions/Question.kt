package com.example.tutorialdiwithkotlin.questions

import com.google.gson.annotations.SerializedName

/**
 * Created by khoado on 05,February,2020
 */
class Question(title: String, id: String) {
    @SerializedName("title")
    private val mTitle: String = title
    @SerializedName("question_id")
    private val mId: String = id

    fun getTitle(): String {
        return mTitle
    }

    fun getId(): String {
        return mId
    }

}


