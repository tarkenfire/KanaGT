package com.hinodesoftworks.kanagt.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.hinodesoftworks.kanagt.R;
import com.hinodesoftworks.kanagt.util.QuizHistoryListAdapter;
import com.hinodesoftworks.kanagt.util.QuizResult;

import java.util.ArrayList;

public class QuizHistoryDialog extends DialogFragment implements View.OnClickListener {

    private ArrayList<QuizResult> mQuizzes;
    private boolean isSetup = false;

    public void setup(ArrayList<QuizResult> quizzes){
        this.mQuizzes = quizzes;

        isSetup = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!isSetup) throw new IllegalStateException();

        View parent = inflater.inflate(R.layout.dialog_quiz_history, container, false);

        Button closeButton = (Button)parent.findViewById(R.id.quiz_history_button);
        closeButton.setOnClickListener(this);

        ListView historyList = (ListView)parent.findViewById(R.id.quiz_history_list);
        QuizHistoryListAdapter adapter = new QuizHistoryListAdapter(getActivity(),
                                                    R.layout.dialog_quiz_history, mQuizzes);
        historyList.setAdapter(adapter);

        return parent;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }


    @Override
    public void onClick(View v) {
        this.dismissAllowingStateLoss();
    }
}
