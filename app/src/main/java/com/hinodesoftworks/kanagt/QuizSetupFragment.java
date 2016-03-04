package com.hinodesoftworks.kanagt;

import android.annotation.SuppressLint;
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

import com.hinodesoftworks.kanagt.util.CharacterSetListAdapter;
import com.hinodesoftworks.kanagt.util.KanaSet;
import com.hinodesoftworks.kanagt.util.QuizManager;

import java.util.ArrayList;

public class QuizSetupFragment extends Fragment implements View.OnClickListener {

    private OnQuizSetupListener mListener;
    private QuizManager.QuizMode mQuizMode = QuizManager.QuizMode.MODE_HIRA_P_QUIZ;

    private Spinner spinner, modeView;


    public QuizSetupFragment() {
        // Required empty public constructor
    }

    public void setQuizMode(QuizManager.QuizMode mode){
        mQuizMode = mode;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View holder = inflater.inflate(R.layout.fragment_quiz_setup, container, false);

        TextView titleView = (TextView)holder.findViewById(R.id.quiz_setup_title);
        ArrayList<KanaSet> kanaSets = new ArrayList<>();
        String[] titles = getActivity().getResources().getStringArray(R.array.kana_set_titles);
        String[] descs = getActivity().getResources().getStringArray(R.array.hira_set_descs);

        switch (mQuizMode){
            case MODE_HIRA_P_QUIZ:
                titleView.setText("Hiragana Practice Quiz");
                descs = getActivity().getResources().getStringArray(R.array.hira_set_descs);
                break;
            case MODE_HIRA_R_QUIZ:
                titleView.setText("Hiragana Ranking Quiz");
                descs = getActivity().getResources().getStringArray(R.array.hira_set_descs);
                break;
            case MODE_KATA_P_QUIZ:
                descs = getActivity().getResources().getStringArray(R.array.kata_set_descs);
                titleView.setText("Katakana Practice Quiz");
                break;
            case MODE_KATA_R_QUIZ:
                descs = getActivity().getResources().getStringArray(R.array.kata_set_descs);
                titleView.setText("Katakana Ranking Quiz");
                break;
        }

        //setup list choices
        for (int i = 0; i < titles.length ; i++){
            KanaSet set = KanaSet.getInstance();
            set.title = titles[i];
            set.desc = descs[i];

            kanaSets.add(set);
        }

        modeView = (Spinner)holder.findViewById(R.id.quiz_setup_set_spinner);
        CharacterSetListAdapter csadapter = new CharacterSetListAdapter(getActivity(),
                                        R.layout.row_kana_set, kanaSets);

        modeView.setAdapter(csadapter);


        //setup spinner
        spinner = (Spinner)holder.findViewById(R.id.quiz_setup_num_of_question_spinner);
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

        int choice = spinner.getSelectedItemPosition();

        int modeChoice = modeView.getSelectedItemPosition();

        int qs = 10;
        switch (choice){
            case 0:
                qs = 5;
                break;
            case 1:
                qs = 10;
                break;
            case 2:
                qs = 15;
                break;
            case 3:
                qs = 20;
                break;
        }


        mListener.onQuizStartButtonPressed(mQuizMode, qs, modeChoice);
    }

    public interface OnQuizSetupListener {
        void onQuizStartButtonPressed(QuizManager.QuizMode mode, int numOfQuestions,
                                             int quizSet);
    }

}
