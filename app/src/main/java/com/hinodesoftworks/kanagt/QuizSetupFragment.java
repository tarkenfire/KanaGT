package com.hinodesoftworks.kanagt;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.hinodesoftworks.kanagt.util.QuizManager;

public class QuizSetupFragment extends Fragment implements View.OnClickListener {

    private OnQuizSetupListener mListener;
    private QuizManager.QuizMode mQuizMode = QuizManager.QuizMode.MODE_HIRA_P_QUIZ;

    public QuizSetupFragment() {
        // Required empty public constructor
    }

    public void setQuizMode(QuizManager.QuizMode mode){
        mQuizMode = mode;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View holder = inflater.inflate(R.layout.fragment_quiz_setup, container, false);

        TextView titleView = (TextView)holder.findViewById(R.id.quiz_setup_title);

        //TODO: Hard coded strings
        switch (mQuizMode){
            case MODE_HIRA_P_QUIZ:
                titleView.setText("Hiragana Practice Quiz");
                break;
            case MODE_HIRA_R_QUIZ:
                titleView.setText("Hiragana Ranking Quiz");
                break;
            case MODE_KATA_P_QUIZ:
                titleView.setText("Katakana Practice Quiz");
                break;
            case MODE_KATA_R_QUIZ:
                titleView.setText("Katakana Ranking Quiz");
                break;
        }

        //setup spinner
        Spinner spinner = (Spinner)holder.findViewById(R.id.quiz_setup_num_of_question_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.quiz_spinner_choices, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //set ui and text for checkboxes

        //set button handler
        Button subButton = (Button)holder.findViewById(R.id.quiz_setup_button);
        subButton.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnQuizSetupListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        mListener.onQuizStartButtonPressed(mQuizMode);
    }

    public interface OnQuizSetupListener {
        public void onQuizStartButtonPressed(QuizManager.QuizMode mode);
    }

}
