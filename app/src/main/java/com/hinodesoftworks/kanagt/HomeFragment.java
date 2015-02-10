package com.hinodesoftworks.kanagt;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private OnHomeFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View holder = inflater.inflate(R.layout.fragment_welcome, container, false);

        Button hiraList, hiraChart, hiraQuiz, kataList, kataChart, kataQuiz;

        hiraList = (Button)holder.findViewById(R.id.welcome_hira_list_button);
        hiraChart = (Button)holder.findViewById(R.id.welcome_hira_chart_button);
        hiraQuiz = (Button)holder.findViewById(R.id.welcome_hira_quiz_button);

        kataList = (Button)holder.findViewById(R.id.welcome_kata_list_button);
        kataChart = (Button)holder.findViewById(R.id.welcome_kata_chart_button);
        kataQuiz = (Button)holder.findViewById(R.id.welcome_kata_quiz_button);

        hiraList.setOnClickListener(this);
        hiraChart.setOnClickListener(this);
        hiraQuiz.setOnClickListener(this);

        kataList.setOnClickListener(this);
        kataChart.setOnClickListener(this);
        kataQuiz.setOnClickListener(this);

        return holder;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnHomeFragmentInteractionListener) activity;
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
        mListener.onQuickLinkPressed(v);
    }

    public interface OnHomeFragmentInteractionListener {
        public void onQuickLinkPressed(View view);
    }

}
