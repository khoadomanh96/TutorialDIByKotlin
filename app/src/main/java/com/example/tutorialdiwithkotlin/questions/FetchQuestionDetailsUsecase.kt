package com.example.tutorialdiwithkotlin.questions

import com.example.tutorialdiwithkotlin.Constants
import com.example.tutorialdiwithkotlin.common.BaseObservable
import com.example.tutorialdiwithkotlin.networking.SingleQuestionResponseSchema
import com.example.tutorialdiwithkotlin.networking.StackoverflowAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by khoado on 12,February,2020
 */

class FetchQuestionDetailsUsecase() : BaseObservable<FetchQuestionDetailsUsecase.Listener>() {
    interface Listener {
        fun onFetchOfQuestionDetailsSucceeded(question: QuestionWithBody)
        fun onFetchOfQuestionDetailsFailed()
    }

    private val mStackOverflowApi: StackoverflowAPI

    private var mCall: Call<SingleQuestionResponseSchema>? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mStackOverflowApi = retrofit.create(StackoverflowAPI::class.java)
    }

    fun fetchQuestionDetailsAndNotify(questionId: String) {
        cancelCurrentFetchIfActive()
        mCall = mStackOverflowApi.questionDetails(questionId)
        mCall?.enqueue(object : Callback<SingleQuestionResponseSchema> {
            override fun onFailure(call: Call<SingleQuestionResponseSchema>, t: Throwable) {
                notifyFailed()
            }

            override fun onResponse(
                call: Call<SingleQuestionResponseSchema>,
                response: Response<SingleQuestionResponseSchema>
            ) {
                if (response.isSuccessful) {
                    notifySucceeded(response.body()?.getQuestion())
                } else {
                    notifyFailed()
                }
            }

        })
    }
    private fun cancelCurrentFetchIfActive() {
        mCall?.apply {
            if (!isCanceled && !isExecuted) {
                cancel()
            }
        }

    }
    private fun notifySucceeded(question: QuestionWithBody?) {
        for (listener in getListeners()) {
            question?.let {
                listener.onFetchOfQuestionDetailsSucceeded(question)
            }
        }
    }

    private fun notifyFailed() {
        for (listener in getListeners()) {
            listener.onFetchOfQuestionDetailsFailed()
        }
    }



}