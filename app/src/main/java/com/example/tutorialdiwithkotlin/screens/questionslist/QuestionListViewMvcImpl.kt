package com.example.tutorialdiwithkotlin.screens.questionslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tutorialdiwithkotlin.R
import com.example.tutorialdiwithkotlin.questions.Question
import com.example.tutorialdiwithkotlin.screens.common.mvcviews.BaseViewMvc
import com.example.tutorialdiwithkotlin.screens.questionslist.QuestionListViewMvcImpl.QuestionsAdapter.QuestionViewHolder

/**
 * Created by khoado on 06,February,2020
 */

class QuestionListViewMvcImpl(
    inflater: LayoutInflater,
    container: ViewGroup?
) : BaseViewMvc<QuestionsListViewMvc.Listener>(),QuestionsListViewMvc {


    private var mRecyclerView : RecyclerView? = null
    private var mQuestionsAdapter : QuestionsAdapter?=null

    init {
        setRootView(inflater.inflate(R.layout.layout_questions_list,container,false))

        mRecyclerView = findViewById(R.id.recycler)
        mRecyclerView?.layoutManager = LinearLayoutManager(getContext())
        mQuestionsAdapter = QuestionsAdapter(
                object : OnQuestionClickListener {
                    override fun onQuestionClicked(question: Question) {
                        for (listener in getListeners()) {
                            listener.onQuestionClicked(question)
                        }
                    }

                })
        mRecyclerView?.adapter = mQuestionsAdapter

    }


    override fun bindQuestions(question : List<Question>) {
        mQuestionsAdapter?.bindData(question)
    }


    interface OnQuestionClickListener {
        fun onQuestionClicked(question : Question)
    }

    class QuestionsAdapter(onQuestionClickListener: OnQuestionClickListener) :
        RecyclerView.Adapter<QuestionViewHolder>() {


        class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val mTitle : TextView = itemView.findViewById(R.id.txt_title)
        }


        private val mOnQuestionClickListener : OnQuestionClickListener = onQuestionClickListener
        private var mQuestionList = ArrayList<Question>()
        fun bindData(question : List<Question>) {
            mQuestionList = ArrayList(question)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
            val iteamView = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_question_list_item,parent,false)
            return QuestionViewHolder(iteamView)
        }

        override fun getItemCount(): Int {
            return mQuestionList.size
        }

        override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
            holder.mTitle.text = mQuestionList[position].getTitle()

            holder.itemView.setOnClickListener {
                mOnQuestionClickListener.onQuestionClicked(mQuestionList[position]) }
        }

    }

}