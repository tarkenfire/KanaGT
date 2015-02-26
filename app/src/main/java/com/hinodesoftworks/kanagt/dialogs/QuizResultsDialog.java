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
import java.util.concurrent.TimeUnit;

public class QuizResultsDialog extends DialogFragment implements View.OnClickListener {

    private ArrayList<Question> mQuestions;
    private QuizManager.QuizMode mQuizMode;
    private boolean isSetup = false;
    private OnPostQuizResultsListener mListener;
    private int[] mScores;
    private long mTimeTaken;

    public void setup(ArrayList<Question> questions, QuizManager.QuizMode quizMode,
                      int[] scores, long timeTaken){
        this.mQuestions = questions;
        this.mQuizMode = quizMode;
        this.mScores = scores;
        this.mTimeTaken = timeTaken;

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
        TextView scoreText = (TextView)parent.findViewById(R.id.quiz_dialog_score_display);
        ListView reviewList = (ListView)parent.findViewById(R.id.quiz_dialog_review_list);
        TextView warningText = (TextView)parent.findViewById(R.id.quiz_dialog_warning_display);
        TextView titleText = (TextView)parent.findViewById(R.id.quiz_dialog_header_title);

        endButton.setOnClickListener(this);

        //set display text
        //TODO: hard coded strings
        switch (mQuizMode){
            case MODE_HIRA_P_QUIZ:
                warningText.setText("This was a HIRAGANA PRACTICE quiz. Your character rankings " +
                        "and quiz stats will NOT be changed.");
                break;
            case MODE_HIRA_R_QUIZ:
                warningText.setText("This was a HIRAGANA RANKING quiz. Your character rankings " +
                        "and quiz stats WILL be changed.");
                break;
            case MODE_KATA_P_QUIZ:
                warningText.setText("This was a KATAKANA PRACTICE quiz. Your character rankings " +
                        "and quiz stats will NOT be changed.");
                break;
            case MODE_KATA_R_QUIZ:
                warningText.setText("This was a KATAKANA RANKING quiz. Your character rankings " +
                        "and quiz stats WILL be changed.");
                break;
        }

        //score
        int correct = mScores[0], incorrect = mScores[1];
        int total = mQuestions.size();
        float percentCorrect = (correct * 100 ) / total , percentIncorrect = (incorrect * 100) / total;


        scoreText.setText( "Correct: " + correct + " (" + percentCorrect + "%)" + " " +
                            "Incorrect: " + incorrect + " (" + percentIncorrect + "%)");


        long seconds = TimeUnit.SECONDS.convert(mTimeTaken, TimeUnit.MILLISECONDS);
        String secondsFormat = "" + seconds;
        if (seconds < 10){
            secondsFormat = "0" + secondsFormat;
        }

        titleText.setText("RESULTS - " + TimeUnit.MINUTES.convert(mTimeTaken, TimeUnit.MILLISECONDS)
                            + ":" + secondsFormat);

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
        this.dismissAllowingStateLoss();
    }

    public interface OnPostQuizResultsListener{
        public void onQuizResultsClosed();
    }
}
