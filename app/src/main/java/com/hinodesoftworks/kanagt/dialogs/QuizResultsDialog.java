package com.hinodesoftworks.kanagt.dialogs;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.R;
import com.hinodesoftworks.kanagt.util.Question;
import com.hinodesoftworks.kanagt.util.QuizManager;
import com.hinodesoftworks.kanagt.util.QuizResultsListAdapter;

import java.util.ArrayList;

public class QuizResultsDialog extends DialogFragment implements View.OnClickListener {

    private ArrayList<Question> mQuestions;
    private QuizManager.QuizMode mQuizMode;
    private boolean isSetup = false;
    private OnPostQuizResultsListener mListener;

    public void setup(ArrayList<Question> questions, QuizManager.QuizMode quizMode){
        this.mQuestions = questions;
        this.mQuizMode = quizMode;

        isSetup = true;
    }

    public void setListener(OnPostQuizResultsListener listener){
        this.mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!isSetup) throw new IllegalStateException();

        View parent = inflater.inflate(R.layout.dialog_quiz_results, container, false);

        //UI handles
        Button endButton = (Button)parent.findViewById(R.id.quiz_dialog_end_button);
        ListView reviewList = (ListView)parent.findViewById(R.id.quiz_dialog_review_list);
        TextView warningText = (TextView)parent.findViewById(R.id.quiz_dialog_warning_display);

        endButton.setOnClickListener(this);

        //set display text
        switch (mQuizMode){
            case MODE_HIRA_P_QUIZ:
                warningText.setText("TODO: HiraP Warning Text");
                break;
            case MODE_HIRA_R_QUIZ:
                warningText.setText("TODO: HiraR Warning Text");
                break;
            case MODE_KATA_P_QUIZ:
                warningText.setText("TODO: KataP Warning Text");
                break;
            case MODE_KATA_R_QUIZ:
                warningText.setText("TODO: KataR Warning Text");
                break;
        }

        QuizResultsListAdapter adapter = new QuizResultsListAdapter(getActivity(), R.layout.row_quiz_answer,
                mQuestions);

        reviewList.setAdapter(adapter);

        return parent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mListener.onQuizResultsClosed();
    }

    @Override
    public void onClick(View v) {
        mListener.onQuizResultsClosed();
        this.dismissAllowingStateLoss();
    }



    public interface OnPostQuizResultsListener{
        public void onQuizResultsClosed();
    }


}
