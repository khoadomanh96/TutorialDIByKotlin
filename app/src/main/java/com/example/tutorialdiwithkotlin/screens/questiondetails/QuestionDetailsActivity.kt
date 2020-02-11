package com.example.tutorialdiwithkotlin.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.tutorialdiwithkotlin.Constants
import com.example.tutorialdiwithkotlin.networking.SingleQuestionResponseSchema
import com.example.tutorialdiwithkotlin.networking.StackoverflowAPI
import com.example.tutorialdiwithkotlin.screens.common.ServerErrorDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by khoado on 06,February,2020
 */

class QuestionDetailsActivity : AppCompatActivity(),
    Callback<SingleQuestionResponseSchema>, QuestionDetailsViewMvc.Listener {

    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"

        fun start(context: Context, questionID: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionID)
            context.startActivity(intent)
        }
    }

    private var mStackoverflowApi: StackoverflowAPI? = null
    private var mCall: Call<SingleQuestionResponseSchema>? = null
    private var mQuestionId: String? = null
    private var mViewMvc: QuestionDetailsViewMvc? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewMvc = QuestionDetailsViewMvcImpl(LayoutInflater.from(this), null)

        setContentView((mViewMvc as QuestionDetailsViewMvcImpl).getRootView())

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mStackoverflowApi = retrofit.create(StackoverflowAPI::class.java)

        mQuestionId = intent.extras?.getString(EXTRA_QUESTION_ID)?:""

    }

    override fun onStart() {
        super.onStart()
        mViewMvc?.registerListener(this@QuestionDetailsActivity)
        mCall = mStackoverflowApi?.questionDetails(mQuestionId?:"")
        mCall?.enqueue(this)
    }

    override fun onStop() {
        super.onStop()
        mViewMvc?.unregisterListener(this@QuestionDetailsActivity)
        mCall?.apply {
            cancel()
        }
    }

    override fun onFailure(call: Call<SingleQuestionResponseSchema>, t: Throwable?) {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(),null)
            .commitAllowingStateLoss()
    }

    override fun onResponse(
        call: Call<SingleQuestionResponseSchema>,
        response: Response<SingleQuestionResponseSchema>
    ) {
        val questionResponseSchema = response.body()
        if (response.isSuccessful && questionResponseSchema!=null) {
            mViewMvc?.bindQuestion(questionResponseSchema.getQuestion())
        } else {
            onFailure(call,null)
        }
    }

}