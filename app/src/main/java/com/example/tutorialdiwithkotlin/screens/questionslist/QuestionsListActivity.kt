package com.example.tutorialdiwithkotlin.screens.questionslist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorialdiwithkotlin.Constants
import com.example.tutorialdiwithkotlin.networking.QuestionsListResponseSchema
import com.example.tutorialdiwithkotlin.networking.StackoverflowAPI
import com.example.tutorialdiwithkotlin.questions.Question
import com.example.tutorialdiwithkotlin.screens.common.ServerErrorDialogFragment
import com.example.tutorialdiwithkotlin.screens.questiondetails.QuestionDetailsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by khoado on 06,February,2020
 */

class QuestionsListActivity : AppCompatActivity(), Callback<QuestionsListResponseSchema>,
    QuestionsListViewMvc.Listener {


    private var mStackoverflowApi: StackoverflowAPI? = null
    private var mCall: Call<QuestionsListResponseSchema>? = null
    private var mViewMvc: QuestionsListViewMvc? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewMvc = QuestionListViewMvcImpl(LayoutInflater.from(this), null)

        setContentView((mViewMvc as QuestionListViewMvcImpl).getRootView())

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mStackoverflowApi = retrofit.create(StackoverflowAPI::class.java)
    }

    override fun onStart() {
        super.onStart()
        mViewMvc?.registerListener(this)
        mCall = mStackoverflowApi?.lastActiveQuestions(20)
        mCall?.enqueue(this)
    }

    override fun onStop() {
        super.onStop()
        mViewMvc?.unregisterListener(this)
        mCall?.cancel()
    }

    override fun onFailure(call: Call<QuestionsListResponseSchema>, t: Throwable?) {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }

    override fun onResponse(
        call: Call<QuestionsListResponseSchema>,
        response: Response<QuestionsListResponseSchema>
    ) {
        val responseSchema = response.body()
        if (response.isSuccessful && responseSchema != null) {
            mViewMvc?.bindQuestions(responseSchema.getQuestions())
        } else {
            onFailure(call, null)
        }
    }

    override fun onQuestionClicked(question: Question) {
        QuestionDetailsActivity.start(context = this@QuestionsListActivity,questionID = question.getId())
    }

}