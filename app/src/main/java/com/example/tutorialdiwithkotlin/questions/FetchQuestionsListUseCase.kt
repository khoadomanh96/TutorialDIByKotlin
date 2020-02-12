package com.example.tutorialdiwithkotlin.questions

import com.example.tutorialdiwithkotlin.Constants
import com.example.tutorialdiwithkotlin.common.BaseObservable
import com.example.tutorialdiwithkotlin.networking.QuestionsListResponseSchema
import com.example.tutorialdiwithkotlin.networking.StackoverflowAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by khoado on 12,February,2020
 */


class FetchQuestionsListUseCase() : BaseObservable<FetchQuestionsListUseCase.Listener>() {
    interface Listener {
        fun onFetchOfQuestionDetailsSucceeded(question: List<Question>)
        fun onFetchOfQuestionDetailsFailed()
    }

    private val mStackoverflowApi : StackoverflowAPI

    private var mCall : Call<QuestionsListResponseSchema>? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mStackoverflowApi = retrofit.create(StackoverflowAPI::class.java)
    }

    fun fetchLastActiveQuestionsAndNotify(numOfQuestions : Int) {
        cancelCurrentFetchIfActive()

        mCall = mStackoverflowApi.lastActiveQuestions(numOfQuestions)
        mCall?.enqueue(object : Callback<QuestionsListResponseSchema> {
            override fun onFailure(call: Call<QuestionsListResponseSchema>, t: Throwable) {
                notifyFailed()
            }

            override fun onResponse(
                call: Call<QuestionsListResponseSchema>,
                response: Response<QuestionsListResponseSchema>
            ) {
                if (response.isSuccessful) {
                    notifySucceeded(response.body()?.getQuestions())
                } else {
                    notifyFailed()
                }
            }

        })
    }


    private fun cancelCurrentFetchIfActive() {
        mCall?.run {
            if (!isCanceled && !isExecuted) {
                cancel()
            }
        }

    }
    private fun notifySucceeded(questions: List<Question>?) {
        questions?.run {
            for (listener in this@FetchQuestionsListUseCase.getListeners()) {
                listener.onFetchOfQuestionDetailsSucceeded(questions)
            }
        }
    }

    private fun notifyFailed() {
        for (listener in getListeners()) {
            listener.onFetchOfQuestionDetailsFailed()
        }
    }



}