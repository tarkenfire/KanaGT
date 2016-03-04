package com.hinodesoftworks.kanagt.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.R;

import java.util.ArrayList;

public class QuizResultsListAdapter extends ArrayAdapter<Question> {
    private final Context ctx;
    private final ArrayList<Question> mQuestions;

    public QuizResultsListAdapter(Context context, int resource, ArrayList<Question> items){
        super (context, resource, items);

        this.ctx = context;
        this.mQuestions = items;

    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    @Override
    public Question getItem(int position) {
        return mQuestions.get(position);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View holder = convertView;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = inflater.inflate(R.layout.row_quiz_answer, parent, false);
        }

        Question question = mQuestions.get(position);
        TextView header = (TextView)holder.findViewById(R.id.row_quiz_header);
        TextView result = (TextView)holder.findViewById(R.id.row_quiz_result);

        header.setText("Question " + (position + 1) + " - " + question.getmDisplayAnswer());
        result.setText("Your answer: " + question.getUserAnswer() + "  " + " Correct answer: " +
                                question.getmRightAnswer());


        if (question.isCorrect()){
            result.setTextColor(Color.parseColor("#40BA46"));
        }
        else{
            result.setTextColor(Color.RED);
        }

        return holder;
    }
}
